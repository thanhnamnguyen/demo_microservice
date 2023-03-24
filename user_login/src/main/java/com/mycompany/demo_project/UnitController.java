/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project;

import com.mycompany.demo_project.unitRepo.service.Receiver;
import com.mycompany.demo_project.unitRepo.service.Sender;
import com.mycompany.demo_project.unitRepo.service.Unit;
import com.mycompany.demo_project.unitRepo.service.UnitService;
import com.mycompany.demo_project.userRepo.User;
import com.mycompany.demo_project.userRepo.UserRepo;
import com.mycompany.demo_project.userRepo.UserService;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
public class UnitController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UnitService unitService;

    @Autowired
    UserService userService;

    @Autowired
    Sender sender;
    @Autowired
    Receiver receiver;

    @GetMapping("/unit")
    public Unit getUnit(@RequestParam String unitId) {
        return unitService.getUnit(unitId);
    }

    @PostMapping("/unit")
    public Unit createUnit(@RequestBody Unit unit) {
        if (unit.getParentUnitId() == null) {
            return null;
        }
        if (!userService.checkAuthor(unit.getParentUnitId())) {
            return null;
        }
        Unit u;
        String correlationId = sender.sendUnitAndForget(unit, "createUnit");

        CompletableFuture<Unit> completableFuture = new CompletableFuture<>();
        completableFuture.complete(receiver.get(correlationId));

        try {
            u = completableFuture.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ExecutionException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        if (u.getId() == null) {
            return null;
        }
        return u;
    }

    @PutMapping("/unit")
    public Unit updateUnit(@RequestBody Unit update) { //not update member

        Unit current = unitService.getUnit(update.getId());
        System.out.println("com.mycompany.demo_project.UnitController.updateUnit()");
        if (current == null) {
            return null;
        }

        if (!userService.checkAuthor(current.getParentUnitId())) {
            return null;
        }
        if (update.getParentUnitId() != null) {
            if (!userService.checkAuthor(update.getParentUnitId())) {
                return null;
            }
        }

        String correlationId = sender.sendUnitAndForget(update, "updateUnit");

        CompletableFuture<Unit> completableFuture = new CompletableFuture<>();
        completableFuture.complete(receiver.get(correlationId));

        try {
            update = completableFuture.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ExecutionException ex) {
            Logger.getLogger(UnitService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        if (update.getId() == null) {
            return null;
        }
        return update;
    }

    @DeleteMapping("/unit")
    public Unit deleteUnit(@RequestParam String unitId) {

        if (!userService.checkAuthor(unitId)) {
            return null;
        }
        Unit unit = new Unit();
        unit.setId(unitId);
        String correlationId = sender.sendUnitAndForget(unit, "deleteUnit");

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

        for (String userId : unit.getUserId()) {
            Optional<User> user = userRepo.findById(userId);
            if (user.isPresent()) {
                user.get().setUnitId(null);
                userRepo.save(user.get());
            }
        }
        return unit;
    }
    //complete test
    //improve by divide sevice in two 2 part: send and receive
    //send all author at one 
}
