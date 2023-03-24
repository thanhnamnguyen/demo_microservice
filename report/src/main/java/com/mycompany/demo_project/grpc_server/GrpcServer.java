/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.grpc_server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class GrpcServer {

    @Autowired
    ReportService rpsr;

    @Autowired
    AuthInc auth;

    public void start() throws IOException, InterruptedException {
        Server srv = ServerBuilder.forPort(6666)
                .intercept(auth)
                .addService(rpsr)
                .build();
        srv.start();
        srv.awaitTermination();

    }
}
