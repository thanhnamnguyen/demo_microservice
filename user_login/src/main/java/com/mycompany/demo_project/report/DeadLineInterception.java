/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.report;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.Deadline;
import io.grpc.MethodDescriptor;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Administrator
 */
public class DeadLineInterception implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> md, CallOptions co, Channel chnl) {
        Deadline d = co.getDeadline();
        if (Objects.isNull(d)) {
            co = co.withDeadline(Deadline.after(4, TimeUnit.SECONDS));
        }
        return chnl.newCall(md, co);
    }
}
