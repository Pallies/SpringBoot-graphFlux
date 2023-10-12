package com.springboot.webflux.graphQL.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor(staticName = "build")
@AllArgsConstructor(staticName = "build")
public class Customer {
    private Integer id;
    private String name;
    private Integer age;
}