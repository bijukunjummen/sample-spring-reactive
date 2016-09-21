package reactive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactive.model.Message;
import reactive.model.MessageAcknowledgement;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;

@Service
public class ReactorService {
	private static final Logger logger = LoggerFactory.getLogger(ReactorService.class);

	public Mono<MessageAcknowledgement> handleMessage(Message message) {
		return Mono
				.delay(Duration.ofMillis(message.getDelayBy()))
				.then(Mono.just(message))
				.map(msg -> Tuples.of(msg, msg.isThrowException()))
				.flatMap(tup -> {
					if (tup.getT2()) {
						return Mono.error(new IllegalStateException("Throwing a deliberate Exception!"));
					}
					Message msg = tup.getT1();
					return Mono.just(new MessageAcknowledgement(msg.getId(), msg.getPayload(), "Response from ReactorService"));
				}).single();
	}

}
