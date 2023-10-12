package com.springboot.webflux.graphQL.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor(staticName = "build")
@AllArgsConstructor(staticName = "build")
public class CustomerOrder {
    private UUID id;
    private String description;
}
