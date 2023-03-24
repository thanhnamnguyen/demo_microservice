/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.report;

import com.mycompany.demo_project.model.ReportServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Administrator
 */
@Configuration
public class GrpcSetup {

    @Bean
    public ReportServiceGrpc.ReportServiceBlockingStub setup() {
        // a channel to client-server communicate
        ManagedChannel mgch = ManagedChannelBuilder.forAddress("localhost", 6666)
                //.intercept(MetadataUtils.newAttachHeadersInterceptor(UserToken.getUserToken(userId)))
                .intercept(new DeadLineInterception())
                .usePlaintext()
                .build();

        return ReportServiceGrpc.newBlockingStub(mgch);
    }

}
