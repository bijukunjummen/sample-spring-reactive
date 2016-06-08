package reactive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactive.model.Message;
import reactive.model.MessageAcknowledgement;
import reactor.core.publisher.Flux;
import reactor.core.tuple.Tuple;

import java.time.Duration;

@Service
public class ReactorService {
	private static final Logger logger = LoggerFactory.getLogger(ReactorService.class);

	public Flux<MessageAcknowledgement> handleMessage(Message message) {
		return Flux.just(message)
				.delay(Duration.ofMillis(message.getDelayBy()))
				.map(msg -> Tuple.of(msg, msg.isThrowException()))
				.flatMap(tup -> {
					if (tup.getT2()) {
						return Flux.error(new IllegalStateException("Throwing a deliberate Exception!"));
					}
					Message msg = tup.getT1();
					return Flux.just(new MessageAcknowledgement(msg.getId(), msg.getPayload(), "Response from ReactorService"));
				});
	}

}
