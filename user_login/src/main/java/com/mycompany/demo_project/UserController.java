/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project;

import com.mycompany.demo_project.loginRepo.ClientDetail;
import com.mycompany.demo_project.unitRepo.service.Sender;
import com.mycompany.demo_project.unitRepo.service.Unit;
import com.mycompany.demo_project.unitRepo.service.UnitService;
import com.mycompany.demo_project.userRepo.User;
import com.mycompany.demo_project.userRepo.UserRepo;
import com.mycompany.demo_project.userRepo.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UnitService unitService;

    @Autowired
    UserService userService;

    @Autowired
    Sender sender;

    @GetMapping("/regis")
    public User createUser(@RequestBody User user) {
        System.out.println(0);
        Unit unit = unitService.getUnit(user.getUnitId());
        if (unit.getId() == null) {
            return null;//error
        }
        User inserted = userRepo.insert(user);
        unitService.saveOneUser(inserted.getId(), unit.getId());
        inserted.setUnitId(unit.getUnitName());
        return inserted;
    }

    @GetMapping("/user/unit")
    public Unit getUnit() {
        ClientDetail client = (ClientDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Unit unit = unitService.getUnit(client.getUser().getUnitId());
        if (unit.getId() == null) {
            return null;//error
        }
        return unit;
    }

    @GetMapping("/user")
    public User getUser(@RequestParam String userId) {
        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent()) {
            return null;
        }

        if (!userService.checkAuthor(user.get().getUnitId())) {
            return null;
        }
        return user.get();
    }

    @DeleteMapping("/user")
    public User deleteUser(@RequestParam String userId) {
        System.out.println("com.mycompany.demo_project.UserController.deleteUser()");
        Optional<User> u = userRepo.findById(userId);
        if (!u.isPresent()) {
            return null;
        }
        if (!userService.checkAuthor(u.get().getUnitId())) {
            return null;
        }

        unitService.removeOneUser(userId, u.get().getUnitId());
        userRepo.deleteById(userId);
        return u.get();
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user) {

        Optional<User> current = userRepo.findById(user.getId());
        if (!current.isPresent()) {
            return null;
        }
        if (!userService.checkAuthor(current.get().getUnitId())) {

            return null;
        }
        if (user.getUnitId() != null) {
            if (!userService.checkAuthor(user.getUnitId())) {

                return null;
            }
        }
        User updated = userService.catUser(user, current.get());
        return userRepo.save(updated);
    }

    @GetMapping("/test")
    public String currentUs() {
        return "this is test page for non login";
    }

    @GetMapping("/test1")
    public String currentUs1() {
        return "this is test page for login";
    }
    //complete test
    //improve by divide sevice in two 2 part: send and receive
    //send all author at one
}
