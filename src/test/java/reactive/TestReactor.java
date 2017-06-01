package reactive;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rx.Observable;
import rx.Single;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestReactor {

    @Test
    public void testReactorAPIs() {
        Flux<String> stringFlux = Flux.just("test").delaySubscription(Duration.ofSeconds(1));
    }

}
