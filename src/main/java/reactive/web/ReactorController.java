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

@RestController
public class ReactorController {

	private final ReactorService aService;

	@Autowired
	public ReactorController(ReactorService aService) {
		this.aService = aService;
	}

	@RequestMapping(path = "/handleMessageReactor", method = RequestMethod.POST)
	public Flux<MessageAcknowledgement> handleMessage(@RequestBody Message message) {
		return this.aService.handleMessage(message);
	}

}
