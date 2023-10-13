package com.springboot.webflux.graphQL.controllers;

import com.springboot.webflux.graphQL.dto.AgeRange;
import com.springboot.webflux.graphQL.dto.Customer;
import com.springboot.webflux.graphQL.dto.CustomerOrder;
import com.springboot.webflux.graphQL.services.CustomerOrderService;
import com.springboot.webflux.graphQL.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService service;
    @Autowired
    private CustomerOrderService orderService;

//        @QueryMapping(name = "customers")
//    public Flux<Customer> getCustomers() {
//        return service.findAllCustomers();
//    }
    @QueryMapping("customers")
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

    /**
     * Argument("filter") AgeRange range
     * permettre de récupérer l'argument filter de type AgeRange
     * déclaration d'un objet au lieu de plusieurs arguments
     */
    @QueryMapping("customersRangeAge")
    public Flux<Customer> getCustomersRange(@Argument("filter") AgeRange range) {
        return service.findByAgeRange(range);
    }

    /**
     * Permet l'association entre les entités Customer et CustomerOrder
     * le typeName détermine le schema du type ou la déclaration est effectuée
     * field détermine le nom du champ dans le type
     * cet appel est effectué par le client graphQL qui va récupérer les données de façon synchrone
     * problème la requête, pour les sous-objets, est effectuée à chaque appel
     * requête effectuer : select * from <TABLE> where <CUSTOMER> = :<CUSTOMER> || N requêtes
     */

    @SchemaMapping(typeName = "Customer", field = "orders")
    public Flux<CustomerOrder> getCustomersAll(Customer customer) {
        return orderService.findOrderByCustomerName(customer.getName());
    }

    /**
     * idem que la méthode précédente mais avec un argument limit
     * variante de la méthode précédente, cette approche permet de limiter les appels à la base de données
     */
    @SchemaMapping(typeName = "Customer", field = "ordersLimit")
    public Flux<CustomerOrder> getCustomersAll(Customer customer, @Argument Integer limit) {
        return orderService.findOrderByCustomerName(customer.getName()).take(limit);
    }
    /**
     * cette approche permet de limiter les appels à la base de données
     * BatchMapping permet d'effectuer une seule requête pour récupérer les données
     * requête effectuer : select * from <TABLE> where <CUSTOMER> in (All:<CUSTOMER>) || 1 requête
     */
    @BatchMapping(typeName = "Customer", field = "ordersBatch")
    public Flux<List<CustomerOrder>> getCustomersBatch(List<Customer> list) {
        return orderService.findByCustomerName(list.stream().map(Customer::getName).collect(toList()));
    }

    /**
     * idem avec une approche différente pour l'agrégation des données
     */
    @BatchMapping(typeName = "Customer", field = "ordersBatch_")
    public Mono<Map<Customer, List<CustomerOrder>>> getCustomersBatch_(List<Customer> list) {
        return orderService.getMap(list);
    }
}
