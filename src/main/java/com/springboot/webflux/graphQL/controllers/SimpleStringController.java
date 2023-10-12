package com.springboot.webflux.graphQL.controllers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@Controller
public class SimpleStringController {
    @QueryMapping
//    @QueryMapping(path = "simpleString")
    public Mono<String> simpleString() {
        return Mono.just("SimpleString text");
    }

    @QueryMapping
    public Mono<String> simpleStringAddArgument(@Argument String name) {
        return Mono.fromSupplier(() -> "SimpleString text1: " + name);
    }

    @QueryMapping
    public Mono<String> simpleStringAddArguments(@Argument String name, @Argument String version) {
        int numVersion = ThreadLocalRandom.current().nextInt(1, 21);
        return Mono.fromSupplier(() -> "SimpleString -v: " + name + " " + (version != null ? version : numVersion));
    }
}
