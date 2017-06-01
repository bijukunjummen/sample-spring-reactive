package sample.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sample.service.RxJavaService;
import rx.Observable;
import rx.Single;
import sample.model.Message;
import sample.model.MessageAcknowledgement;

@RestController
@RequestMapping("/controller")
public class RxJavaController {

    private final RxJavaService aService;

    @Autowired
    public RxJavaController(RxJavaService aService) {
        this.aService = aService;
    }

    @RequestMapping(path = "/handleMessageRxJava", method = RequestMethod.POST)
    public Single<MessageAcknowledgement> handleMessage(@RequestBody Message message) {
        return this.aService.handleMessageSingle(message);
    }

    @RequestMapping(path = "/handleMessageRxJavaObs", method = RequestMethod.POST)
    public Observable<MessageAcknowledgement> handleMessageObs(@RequestBody Message message) {
        return this.aService.handleMessageObservable(message);
    }

}
