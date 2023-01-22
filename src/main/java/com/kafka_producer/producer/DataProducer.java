package com.kafka_producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka_producer.Model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class DataProducer {
    @Autowired
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    String topic="employeedata";

    public void sendData(Employee empdata) throws JsonProcessingException {
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
    public SendResult<Integer, String> sendDataSynchronous(Employee empdata) throws JsonProcessingException, ExecutionException, InterruptedException {
        Integer key = empdata.getId();
        String value = objectMapper.writeValueAsString(empdata);

        SendResult<Integer, String> sendResult;
        try {
            sendResult = kafkaTemplate.sendDefault(key, value).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("InterruptedException |ExecutionException Sending MSG : {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception Sending MSG : {}", e.getMessage());
            throw e;
        }
        return sendResult;


    }
    public void sendDataapproch2(Employee empdata) throws JsonProcessingException {
        Integer key= empdata.getId();
        String value=objectMapper.writeValueAsString(empdata);
        ProducerRecord<Integer,String> producerRecord=buildproducerrecord(key,value,topic);
        ListenableFuture<SendResult<Integer,String>> listenableFuture= kafkaTemplate.send(producerRecord);
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

    private ProducerRecord<Integer, String> buildproducerrecord(Integer key, String value, String topic) {
        return new ProducerRecord<>(topic, key, value);
    }
}
