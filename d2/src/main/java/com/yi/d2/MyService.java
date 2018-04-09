package com.yi.d2;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class MyService {
    public static final Map<String, User> data = new HashMap<>();

    public Mono<User> createOrUpdate(final User user) {
        data.put(user.getId(), user);
        return Mono.just(user);
    }

    public Flux<User> createOrUpdate(final Flux<User> users) {
        return users.doOnNext(user -> data.put(user.getId(), user));
    }

    public Flux<User> list() {
        return Flux.fromIterable(data.values());
    }

    public Flux<User> getById(final Flux<String> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(data.get(id)));
    }

    public Mono<User> getById(final String id) {
        return Mono.justOrEmpty(data.get(id)).switchIfEmpty(Mono.error(new Exception("resource not found")));
    }

    public Mono<User> delete(final String id) {
        return Mono.justOrEmpty(data.remove(id));
    }
}
