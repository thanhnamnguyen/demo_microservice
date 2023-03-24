/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.unitRepo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class UnitService {

    @Autowired
    Sender sender;

    @Autowired
    Receiver receiver;

    public Unit getUnit(String Id) {
        if (Id == null) {
            return null;
        }
        Unit unit = new Unit();
        unit.setId(Id);
        String correlationId = sender.sendUnitAndForget(unit, "getUnit");
        CompletableFuture<Unit> completableFuture = new CompletableFuture<>();
        completableFuture.complete(receiver.get(correlationId));

        try {
            unit = completableFuture.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ExecutionException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        if (unit.getId() == null) {
            return null;
        }
        return unit;
    }

    public Unit saveOneUser(String userId, String unitId) {
        if (userId == null || unitId == null) {
            return null;
        }
        Unit unit = new Unit();
        unit.setId(unitId);
        List<String> a = new ArrayList<>();
        a.add(userId);
        unit.setUserId(a);
        String correlationId = sender.sendUnitAndForget(unit, "saveOneUser");

        CompletableFuture<Unit> completableFuture = new CompletableFuture<>();
        completableFuture.complete(receiver.get(correlationId));
        try {
            unit = completableFuture.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return unit;
    }

    public Unit removeOneUser(String userId, String unitId) {
        if (userId == null || unitId == null) {
            return null;
        }
        Unit unit = new Unit();
        unit.setId(unitId);
        List<String> a = new ArrayList<>();
        a.add(userId);
        unit.setUserId(a);

        String correlationId = sender.sendUnitAndForget(unit, "removeOneUser");

        CompletableFuture<Unit> completableFuture = new CompletableFuture<>();
        completableFuture.complete(receiver.get(correlationId));
        try {
            unit = completableFuture.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return unit;
    }

}
