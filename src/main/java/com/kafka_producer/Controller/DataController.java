package com.kafka_producer.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka_producer.Employee.emp_data;
import com.kafka_producer.producer.DataProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DataController {
    @Autowired
    DataProducer dataProducer;

    @PostMapping("/em")
    public ResponseEntity<emp_data> postInfo(@RequestBody emp_data empdata) throws JsonProcessingException {
        log.info("before sending data");
        dataProducer.sendData(empdata);
        log.info("after sending data");
        return ResponseEntity.status(HttpStatus.CREATED).body(empdata);

    }
}
