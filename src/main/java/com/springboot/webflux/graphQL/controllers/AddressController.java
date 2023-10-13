package com.springboot.webflux.graphQL.controllers;

import com.springboot.webflux.graphQL.SuppressUnusedParameterWarning;
import com.springboot.webflux.graphQL.dto.Address;
import com.springboot.webflux.graphQL.dto.Customer;
import com.springboot.webflux.graphQL.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class AddressController {

    @Autowired
    private AddressService service;

    @SchemaMapping(typeName = "Customer")
    @SuppressUnusedParameterWarning
    public Mono<Address> address(Customer customer) {
        return service.findAddress();
    }


}
