package sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sample.model.Message;
import sample.utils.Util;
import sample.model.MessageAcknowledgement;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CompletableFutureService {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureService.class);

    private final ExecutorService futureExecutor = Executors.newFixedThreadPool(10);

    public CompletableFuture<MessageAcknowledgement> handleMessage(Message message) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Start: Executing slow task in CompletableFutureService");
            Util.delay(message.getDelayBy());
            if (message.getThrowException()) {
                throw new RuntimeException("Throwing a deliberate Exception!");
            }
            logger.info("End: Executing slow task in CompletableFutureService");
            return new MessageAcknowledgement(message.getId(), message.getPayload(), "data from CompletableFutureService");
        }, futureExecutor);
    }

}
