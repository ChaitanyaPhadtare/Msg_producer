package com.kafka_producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutocreateConfig {

    @Bean
    public NewTopic info(){
        return TopicBuilder.name("employeedata")
                .partitions(4)
                .replicas(1)
                .build();
    }
}
