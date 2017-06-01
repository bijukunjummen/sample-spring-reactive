package sample.web

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import sample.model.Message
import sample.model.MessageAcknowledgement
import java.time.Duration


object ApplicationRoutes {

    fun routes(): RouterFunction<*> {
        return nest(accept(MediaType.APPLICATION_JSON).and(path("/reactive")),
                route(POST("/handleMessage"),
                        HandlerFunction { r ->
                            val messageMono = r.bodyToMono(Message::class.java)
                            messageMono.flatMap { message ->
                                Mono
                                        .delay(Duration.ofMillis(message.delayBy))
                                        .flatMap({ d ->
                                            if (message.throwException) {
                                                ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                                            } else {
                                                ServerResponse.ok()
                                                        .body(
                                                                Mono.just(MessageAcknowledgement(message.id, message.payload, "payload")),
                                                                MessageAcknowledgement::class.java)
                                            }
                                        })
                            }

                        }
                ))
    }
}