package com.jesperdj.example.reactivefakebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ReactiveFakebookHandler {

    private final FakePostRepository repository;

    @Autowired
    public ReactiveFakebookHandler(FakePostRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(repository.findAllByOrderByTimestampDesc(), FakePost.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");

        return repository
                .findById(id)
                .flatMap(post -> ok().contentType(APPLICATION_JSON).body(fromObject(post)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request
                .bodyToMono(FakePost.class)
                .doOnNext(post -> post.setTimestamp(LocalDateTime.now()))
                .flatMap(repository::save)
                .flatMap(post -> ok().contentType(APPLICATION_JSON).body(fromObject(post)));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");

        return repository
                .findById(id)
                .zipWith(request.bodyToMono(FakePost.class))
                .map(tuple -> {
                    FakePost existingPost = tuple.getT1();
                    FakePost updatedPost = tuple.getT2();

                    existingPost.setTimestamp(LocalDateTime.now());
                    existingPost.setContent(updatedPost.getContent());
                    return existingPost;
                })
                .flatMap(repository::save)
                .flatMap(post -> ok().contentType(APPLICATION_JSON).body(fromObject(post)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");

        return repository
                .findById(id)
                .flatMap(post -> repository.delete(post).then(ok().build()))
                .switchIfEmpty(notFound().build());
    }
}
