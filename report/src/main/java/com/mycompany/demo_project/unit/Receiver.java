/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.unit;

import java.util.HashMap;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component
public class Receiver {

    private HashMap<String, Unit> unitStore = new HashMap<String, Unit>();

    @RabbitListener(queues = "UnitResponse")
    public void receive(Unit unit, Message message) {
        String correlationId
                = message.getMessageProperties().getCorrelationId();
        unitStore.put(correlationId, unit);
    }

    public Unit get(String correlationId) {

        try {
            while (!unitStore.containsKey(correlationId)) {
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return unitStore.get(correlationId);
    }
}
