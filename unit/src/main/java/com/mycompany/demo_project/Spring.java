/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project;

import com.mycompany.demo_project.data.Unit;
import com.mycompany.demo_project.service.UnitService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableMongoRepositories
public class Spring implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Bean
//    public Queue myQueue() {
//        return new Queue("myQueue1", false);
//    }
    public static void main(String[] args) {
        SpringApplication.run(Spring.class, args);
    }

    @Autowired
    public UnitService ctrl;

    @Override
    public void run(String... args) {
//        System.out.println("hello");
//        Unit unit = new Unit();
//        unit.setUnitName("dai doi 1");
//        unit = ctrl.createUnit(unit);

        Unit unit = ctrl.getUnit("63aa622384e8594003472a2a");
        if (unit != null) {
            System.out.println(unit.getUnitName());
        } else {
            System.out.println("null");
        }
    }
}
