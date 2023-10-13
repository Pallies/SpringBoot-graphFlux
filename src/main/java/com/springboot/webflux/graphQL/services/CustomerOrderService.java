package com.springboot.webflux.graphQL.services;

import com.springboot.webflux.graphQL.dto.Customer;
import com.springboot.webflux.graphQL.dto.CustomerOrder;
import io.netty.util.internal.ThreadLocalRandom;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerOrderService {
    private final Map<String, List<CustomerOrder>> map = Map.of(
            "john", List.of(
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder John " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder John " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder John " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder John " + ThreadLocalRandom.current().nextInt(1, 100))
            ),
            "peter", List.of(
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Peter " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Peter " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Peter " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Peter " + ThreadLocalRandom.current().nextInt(1, 100))
            ),
            "kate", List.of(
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Kate " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Kate " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Kate " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Kate " + ThreadLocalRandom.current().nextInt(1, 100))
            ),
            "joseph", List.of(
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Joseph " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Joseph " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Joseph " + ThreadLocalRandom.current().nextInt(1, 100)),
                    CustomerOrder.build(UUID.randomUUID(), "CustomerOrder Joseph " + ThreadLocalRandom.current().nextInt(1, 100))
            )
    );

    //    simule une requête de style select * from TABLE where name = :name
    // retourne un flux d'une liste dont le paramètre est la clé du map ou par défaut une liste vide
    public Flux<CustomerOrder> findOrderByCustomerName(String name) {
        return Flux.fromIterable(map.getOrDefault(name.toLowerCase(), Collections.emptyList()))
                .delayElements(Duration.ofSeconds(1));
    }
//----------------------------------------------------------------------------------------------------
    //    simule une requête de style select * from TABLE where name in (:names)
    // /!\   toujours renvoyer une liste vide sur un objet obligatoire

    // METHODE 1 : liste ou liste vide convertie en flux
//    public Flux<List<CustomerOrder>> findByCustomerNameOrDefault(List<String> names) {
//        return Flux.fromIterable(names)
//                .map(this::findByCustomerNameListOrDefault);
//    }

    private List<CustomerOrder> findByCustomerNameListOrDefault(String name) {
        return map.getOrDefault(name.toLowerCase(), Collections.emptyList());
    }

    // METHODE 2 : liste effectuée puis convertie en flux avec une liste ou une liste vide
//    flapMap traite de façon parrallèle les flux donc pour éviter que les réponses du CustomerOrder soit en correspondance avec le Customer
//    on utilise un flux séquentiel
    public Flux<List<CustomerOrder>> findByCustomerName(List<String> names) {
        return Flux.fromIterable(names)
                .flatMapSequential(n -> findByCustomerNameList(n).defaultIfEmpty(Collections.emptyList()));
    }

    private Mono<List<CustomerOrder>> findByCustomerNameList(String name) {
        return Mono.justOrEmpty(map.get(name.toLowerCase()));
    }

    /*
    * METHODE 3 : une autre approche pour éviter le traitement par un flapMap
    * traitement plus rapide
    */
    public Mono<Map<Customer, List<CustomerOrder>>> getMap(List<Customer> customers) {
        return Flux.fromIterable(customers)
                .map(c -> Tuples.of(c, findByCustomerNameListOrDefault(c.getName())))
                .collectMap(Tuple2::getT1, Tuple2::getT2);

    }
    //----------------------------------------------------------------------------------------------------


}
