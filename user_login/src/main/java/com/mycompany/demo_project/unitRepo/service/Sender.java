/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.unitRepo.service;

import java.util.UUID;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class Sender {

    @Autowired
    private RabbitTemplate template;

    public String sendUnitAndForget(Unit unit, String consumerTag) {

        String correlationId = UUID.randomUUID().toString();

        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            messageProperties.setReplyTo("UnitResponse");
            messageProperties.setCorrelationId(correlationId);
            messageProperties.setType(consumerTag);
            return message;
        };

        template.convertAndSend("UnitRequest",
                unit,
                messagePostProcessor);
        return correlationId;
    }
}
