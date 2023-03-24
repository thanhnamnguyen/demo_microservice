/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project;

import com.mycompany.demo_project.grpc_server.GrpcServer;
import com.mycompany.demo_project.repo.ReportRepo;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableMongoRepositories
public class Spring implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Spring.class, args);
    }

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

    @Autowired
    ReportRepo repo;

    @Autowired
    GrpcServer server;

    @Override
    public void run(String... args) throws Exception {
        //server.start();

    }
}
