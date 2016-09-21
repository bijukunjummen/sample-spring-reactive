package reactive.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactive.model.Message;
import reactive.model.MessageAcknowledgement;
import reactive.service.RxJavaService;
import rx.Observable;
import rx.Single;

@RestController
public class RxJavaController {

	private final RxJavaService aService;

	@Autowired
	public RxJavaController(RxJavaService aService) {
		this.aService = aService;
	}

	@RequestMapping(path = "/handleMessageRxJava", method = RequestMethod.POST)
	public Single<MessageAcknowledgement> handleMessage(@RequestBody Message message) {
		System.out.println("Got Message..");
		return this.aService.handleMessage(message);
	}

}
