/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.report;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import java.util.concurrent.Executor;

/**
 *
 * @author Administrator
 */
public class UserSension extends CallCredentials {

    private String jwt;
    public static final Metadata.Key<String> USER_TOKEN
            = Metadata.Key.of("user-token", Metadata.ASCII_STRING_MARSHALLER);

    public UserSension(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public void applyRequestMetadata(RequestInfo ri, Executor exctr, MetadataApplier ma) {

        exctr.execute(() -> {
            Metadata metadata = new Metadata();
            metadata.put(USER_TOKEN, this.jwt);
            ma.apply(metadata);

        });
    }

    @Override
    public void thisUsesUnstableApi() {
        //null for now
    }

}
