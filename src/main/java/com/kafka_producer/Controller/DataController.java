package com.kafka_producer.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka_producer.Model.Employee;
import com.kafka_producer.producer.DataProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class DataController {
    @Autowired
    DataProducer dataProducer;

    @PostMapping("/em")
    public ResponseEntity<Employee> postInfo(@RequestBody Employee empdata) throws JsonProcessingException, ExecutionException, InterruptedException {
        log.info("before sending data");
       // dataProducer.sendData(empdata);
        //SendResult<Integer, String> sendResult=dataProducer.sendDataSynchronous(empdata);
        dataProducer.sendDataapproch2(empdata);
        //log.info("Sendresult is {}",sendResult.toString());
        log.info("after sending data");
        return ResponseEntity.status(HttpStatus.CREATED).body(empdata);

    }
}
