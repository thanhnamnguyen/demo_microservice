/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.userRepo;

import com.mycompany.demo_project.unitRepo.service.Receiver;
import com.mycompany.demo_project.unitRepo.service.Sender;
import com.mycompany.demo_project.unitRepo.service.Unit;
import com.mycompany.demo_project.unitRepo.service.UnitService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    UnitService unitService;

    @Autowired
    Sender sender;

    @Autowired
    Receiver receiver;

    public boolean checkAuthor(String unitId) {
        if (unitId == null) {
            return true;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            return false;
        }
        String clientId = authentication.getName();
        Unit u = new Unit();
        u.setId(unitId);
        u.setCommanderId(clientId);
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

    public User catUser(User updated, User current) {
        if (updated.getName() == null) {
            updated.setName(current.getName());
        }
        if (updated.getPass() == null) {
            updated.setPass(current.getPass());
        }
        if (updated.getUnitId() == null || updated.getUnitId().equalsIgnoreCase(current.getUnitId())) {
            updated.setUnitId(current.getUnitId());
        } else {
            //should check author seperate
            unitService.removeOneUser(current.getId(), current.getUnitId());
            unitService.saveOneUser(current.getId(), updated.getUnitId());
        }
        return updated;
    }
}
