package reactive.bkp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestReactorBackpressure {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestReactorBackpressure.class);

    @Test
    public void testSimple() throws Exception {
        long startTime = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(1);

        Observable.interval(107, TimeUnit.MILLISECONDS)
                .debounce(100, TimeUnit.MILLISECONDS)
                .take(10)
                .subscribe(
                        msg -> LOGGER.info(msg + ""),
                        t -> latch.countDown(),
                        () -> latch.countDown()
                );

        latch.await();
    }

    @Test
    public void testWithBackPressure1() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Observable
                .range(1, 1_000_000_000)
                .map(Dish::new)
                .observeOn(Schedulers.io())
                .subscribe(x -> {
                    LOGGER.info("Washing: " + x);
                    sleepMillis(50);
                }, t -> latch.countDown(), () -> latch.countDown());

        latch.await();
    }

    private void sleepMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithBackPressureCustomRange() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        myRange(1, 1000)
                .map(Dish::new)
                .observeOn(Schedulers.io())
                .subscribe(x -> {
                    LOGGER.info("Washing: " + x);
                    sleepMillis(50);
                }, t -> latch.countDown(), () -> latch.countDown());

        latch.await();
    }

    Observable<Integer> myRange(int from, int count) {
        return Observable.create(subscriber -> {
            int i = from;
            while (i < from + count) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(i++);
                } else {
                    return;
                }
            }
            subscriber.onCompleted();
        });
    }

}
