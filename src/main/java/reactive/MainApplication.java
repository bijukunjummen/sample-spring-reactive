package reactive;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.boot.HttpServer;
import org.springframework.http.server.reactive.boot.ReactorHttpServer;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.ResponseStatusExceptionHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import java.util.concurrent.CompletableFuture;

@Configuration
public class MainApplication {

	public static void main(String[] args) throws Exception {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("reactive");
		DispatcherHandler dispatcherHandler = new DispatcherHandler();
		dispatcherHandler.setApplicationContext(context);

		HttpHandler httpHandler = WebHttpHandlerBuilder.webHandler(dispatcherHandler)
				.exceptionHandlers(new ResponseStatusExceptionHandler())
				.build();

		HttpServer server = new ReactorHttpServer();
		server.setPort(8080);
		server.setHandler(httpHandler);
		server.afterPropertiesSet();
		server.start();

		CompletableFuture<Void> stop = new CompletableFuture<>();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			stop.complete(null);
		}));
		synchronized (stop) {
			stop.wait();
		}
	}


}
