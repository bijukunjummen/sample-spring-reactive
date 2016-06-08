package reactive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactive.model.Message;
import reactive.model.MessageAcknowledgement;
import rx.Observable;

import java.util.concurrent.TimeUnit;

@Service
public class RxJavaService {
	private static final Logger logger = LoggerFactory.getLogger(RxJavaService.class);

	public Observable<MessageAcknowledgement> handleMessage(Message message) {
		logger.info("About to Acknowledge");
		return Observable.just(message)
				.delay(message.getDelayBy(), TimeUnit.MILLISECONDS)
				.flatMap(msg -> {
					if (msg.isThrowException()) {
						return Observable.error(new IllegalStateException("Throwing a deliberate exception!"));
					}
					return Observable.just(new MessageAcknowledgement(message.getId(), message.getPayload(), "From RxJavaService"));
				});
	}

}
