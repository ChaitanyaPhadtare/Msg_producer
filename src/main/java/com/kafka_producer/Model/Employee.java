package com.kafka_producer.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Employee {
    private Integer Id;
    private String first_Name;
    private String last_Name;
    private Address address;

}
