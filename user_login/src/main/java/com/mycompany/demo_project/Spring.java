/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableMongoRepositories
public class Spring {

//    @Bean
//    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
//        return new AsyncRabbitTemplate(rabbitTemplate);
//    }
//
//    @Autowired
//    private AsyncRabbitTemplate asyncRabbitTemplate;
    @Bean
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue RequestQueue() {
        return new Queue("UnitRequest", false);
    }

    @Bean
    public Queue ResponseQueue() {
        return new Queue("UnitResponse", false);
    }

    public static void main(String[] args) {
        SpringApplication.run(Spring.class, args);
    }

//    @Override
//    public void run(String... args) {
//        User a = new User();
//        a.setName("hi");
//        AsyncRabbitTemplate.RabbitConverterFuture<Unit> b = asyncRabbitTemplate.convertSendAndReceiveAsType("test", "123", a,
//                new ParameterizedTypeReference<Unit>() {
//        });
//
//        b.addCallback(new ListenableFutureCallback<Unit>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("waiting");
//            }
//
//            @Override
//            public void onSuccess(Unit x) {
//                System.out.println(x.getUnitName());
//            }
//        });
//        System.out.println("order");
//    }
}
