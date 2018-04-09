package com.yi.d2;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MyController {
    private final MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/index")
    public Mono<String> index() {
        return Mono.just("i'm d2 index");
    }

    @GetMapping("/user")
    public Flux<User> list() {
        return myService.list();
    }

    @GetMapping("/user/{id}")
    public Mono<User> get(@PathVariable String id) {
        return myService.getById(id);
    }

    @GetMapping("/user/getByIds")
    public Flux<User> getByIds(String ids) {
        Flux<String> fids = Flux.fromArray(ids.split(","));
        return myService.getById(fids);
    }

    @PostMapping("/user")
    public Mono<User> create(User user) {
        return myService.createOrUpdate(user);
    }

    @PostMapping("/user/create")
    public Flux<User> create(String ids) {
        List<User> list = new ArrayList<>();
        for (String id : ids.split(",")) {
            User user = new User();
            user.setId(id);
            user.setName("name-" + id);
            list.add(user);
        }
        return myService.createOrUpdate(Flux.fromIterable(list));
    }

    @PutMapping("/user")
    public Mono<User> update(User user) {
        return myService.createOrUpdate(user);
    }

    @DeleteMapping("/user/{id}")
    public Mono<User> delete(String id) {
        return myService.delete(id);
    }
}
