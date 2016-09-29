package reactive.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactive.model.Message;
import reactive.model.MessageAcknowledgement;
import reactive.service.ReactorService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReactorController {

	private final ReactorService aService;

	@Autowired
	public ReactorController(ReactorService aService) {
		this.aService = aService;
	}

	@RequestMapping(path = "/handleMessageReactor", method = RequestMethod.POST)
	public Mono<MessageAcknowledgement> handleMessage(@RequestBody Message message) {
		return this.aService.handleMessageMono(message);
	}

	@RequestMapping(path = "/handleMessageReactorFlux", method = RequestMethod.POST)
	public Flux<MessageAcknowledgement> handleMessageFlux(@RequestBody Message message) {
		return this.aService.handleMessageFlux(message);
	}

}
