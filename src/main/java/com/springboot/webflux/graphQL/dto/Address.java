package com.springboot.webflux.graphQL.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor(staticName = "build")
@AllArgsConstructor(staticName = "build")
public class Address {

    private String street;
    private String city;
}
