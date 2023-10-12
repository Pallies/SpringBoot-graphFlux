package com.springboot.webflux.graphQL.controllers;

import com.springboot.webflux.graphQL.dto.AgeRange;
import com.springboot.webflux.graphQL.entities.Customer;
import com.springboot.webflux.graphQL.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService service;

    @QueryMapping(name = "customers")
    public Flux<Customer> getCustomers() {
        return service.findAllCustomers();
    }

    @QueryMapping("customerById")
    public Mono<Customer> getCustomerById(@Argument Integer id) {
        return service.findById(id);
    }

    @QueryMapping("customersByNames")
    public Flux<Customer> getCustomerByContainsName(@Argument String name) {
        return service.findByContainsName(name);
    }
    @QueryMapping("customersRangeAge")
    public Flux<Customer> getCustomersRange(@Argument("filter") AgeRange range){
        return service.findByAgeRange(range);
    }

}
