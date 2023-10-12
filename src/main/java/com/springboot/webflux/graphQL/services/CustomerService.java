package com.springboot.webflux.graphQL.services;

import com.springboot.webflux.graphQL.dto.AgeRange;
import com.springboot.webflux.graphQL.entities.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final Flux<Customer> flux = Flux.just(
            new Customer(1, "John", 20, "New York"),
            new Customer(2, "Mary", 25, "Boston"),
            new Customer(3, "Peter", 30, "Miami"),
            new Customer(4, "Kate", 35, "Los Angeles"),
            new Customer(5, "Joseph", 40, "Chicago")
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
