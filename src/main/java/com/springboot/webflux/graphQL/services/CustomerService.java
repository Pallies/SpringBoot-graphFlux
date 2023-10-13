package com.springboot.webflux.graphQL.services;

import com.springboot.webflux.graphQL.dto.AgeRange;
import com.springboot.webflux.graphQL.dto.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final Flux<Customer> flux = Flux.just(
             Customer.build(1, "John", 20),
             Customer.build(2, "Mary", 25),
             Customer.build(3, "Peter", 30),
             Customer.build(4, "Kate", 35),
             Customer.build(5, "Joseph", 40)
    );

    public Flux<Customer> findAllCustomers() {
        return flux;
    }
    public Mono<Customer> findById(Integer id) {
        return flux
                .filter(customer -> customer.getId().equals(id))
                .next();
    }
    public Flux<Customer> findByContainsName(String name) {
        return flux
                .filter(customer -> customer.getName().toLowerCase().contains(name.toLowerCase()));
    }

    public Flux<Customer> findByAgeRange(AgeRange range) {
        return flux
                .filter(customer -> customer.getAge() >= range.getMin() && customer.getAge() <= range.getMax());
    }
}
