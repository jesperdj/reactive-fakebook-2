package com.jesperdj.example.reactivefakebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@SpringBootApplication
public class ReactiveFakebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveFakebookApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> route(ReactiveFakebookHandler handler) {
        return RouterFunctions
                .route(GET("/posts").and(accept(APPLICATION_JSON)), handler::getAll)
                .andRoute(GET("/posts/{id}").and(accept(APPLICATION_JSON)), handler::getById)
                .andRoute(POST("/posts").and(contentType(APPLICATION_JSON)).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT("/posts/{id}").and(contentType(APPLICATION_JSON)).and(accept(APPLICATION_JSON)), handler::update)
                .andRoute(DELETE("/posts/{id}"), handler::delete);
    }
}
