/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.grpc_server;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class AuthInc implements ServerInterceptor {

    public String userId = "";

    public final Metadata.Key<String> USER_TOKEN
            = Metadata.Key.of("user-token", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> sc, Metadata mtdt, ServerCallHandler<ReqT, RespT> sch) {
        userId = mtdt.get(USER_TOKEN);

        return sch.startCall(sc, mtdt);
    }
}
