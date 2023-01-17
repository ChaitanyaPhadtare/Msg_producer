package com.kafka_producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka_producer.Employee.emp_data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class DataProducer {
    @Autowired
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void sendData(emp_data empdata) throws JsonProcessingException {
        Integer key= empdata.getId();
        String value=objectMapper.writeValueAsString(empdata);
        ListenableFuture<SendResult<Integer,String>> listenableFuture= kafkaTemplate.sendDefault(key,value);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override

            public void onFailure(Throwable ex) {
                handleFailure(key,value,ex);

            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key,value,result);
            }

            });
        }

    private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error Sending MSG : {}",ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in Onfailure: {}",throwable.getMessage());
        }
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Msg sent successfully key: {} and value: {}",key,value);
    };
}
