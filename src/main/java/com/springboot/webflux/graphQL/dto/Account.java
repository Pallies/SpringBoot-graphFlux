package com.springboot.webflux.graphQL.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor(staticName = "build")
@AllArgsConstructor( staticName = "build")
public class Account {
    private UUID id;
    private Integer amount;
    private String accountType;
}
