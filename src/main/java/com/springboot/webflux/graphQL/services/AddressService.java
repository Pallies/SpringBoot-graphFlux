package com.springboot.webflux.graphQL.services;

import com.springboot.webflux.graphQL.SuppressUnusedParameterWarning;
import com.springboot.webflux.graphQL.dto.Address;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AddressService {

    private final Map<Integer, String> street = Map.of(
            ThreadLocalRandom.current().nextInt(1, 200), " Rue de la Liberté",
            ThreadLocalRandom.current().nextInt(1, 200), " Avenue des Roses",
            ThreadLocalRandom.current().nextInt(1, 200), " Rue du Commerce",
            ThreadLocalRandom.current().nextInt(1, 200), " Boulevard de la Plage",
            ThreadLocalRandom.current().nextInt(1, 200), " Rue de la Montagne",
            ThreadLocalRandom.current().nextInt(1, 200), " Avenue de la Paix",
            ThreadLocalRandom.current().nextInt(1, 200), " Rue des Champs",
            ThreadLocalRandom.current().nextInt(1, 200), " Boulevard des Arts",
            ThreadLocalRandom.current().nextInt(1, 200), " Rue de la Mer",
            ThreadLocalRandom.current().nextInt(1, 200), " Avenue de la Forêt"
    );
    private final Map<String, String> city = Map.of(
            "Paris", "75001",
            "Marseille", "13001",
            "Lyon", "69001",
            "Bordeaux", "33000",
            "Toulouse", "31000",
            "Nice", "06000",
            "Lille", "59000",
            "Strasbourg", "67000",
            "Nantes", "44000",
            "Montpellier", "34000"
    );
    @SuppressUnusedParameterWarning
    public Mono<Address> findAddress() {
        int index = ThreadLocalRandom.current().nextInt(0, 9);
        Map.Entry<String, String> cityEntry = city.entrySet().stream().toList().get(index);
        Map.Entry<Integer, String> streetEntry =street.entrySet().stream().toList().get(index);

        String street = streetEntry.getKey() + " " + streetEntry.getValue();
        String city = cityEntry.getValue() + ", " + cityEntry.getKey();
        return Mono.justOrEmpty(Address.build(street, city));
    }
}
