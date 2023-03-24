/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.unit;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitService {

    @Autowired
    Sender sender;

    @Autowired
    Receiver receiver;

    public boolean checkAuthor(String userId, String unitId) {

        Unit u = new Unit();
        u.setId(unitId);
        u.setCommanderId(userId);
        String correlationId = sender.sendUnitAndForget(u, "checkAuthority");
        CompletableFuture<Unit> completableFuture = new CompletableFuture<>();
        completableFuture.complete(receiver.get(correlationId));

        try {
            u = completableFuture.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (u.getId() == null) {
            return false;
        }
        return true;
    }

}
