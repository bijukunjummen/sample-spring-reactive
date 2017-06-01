package sample.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import sample.web.ApplicationRoutes

@Configuration
class RouterFunctionsConfig {
    
    @Bean
    fun routerFunctions(): RouterFunction<*> {
        return ApplicationRoutes.routes()
    } 
}