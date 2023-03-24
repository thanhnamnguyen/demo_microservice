/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.controller;

import com.mycompany.demo_project.data.Unit;
import com.mycompany.demo_project.service.UnitService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class UnitController {

    @Autowired
    private UnitService services;

    //convert to json to send
    @RabbitListener(queues = "UnitRequest")
    public Unit listen(Unit unit, Message message) {

        switch (message.getMessageProperties().getType()) {
            case "getUnit":
                System.out.println("com.mycompany.demo_project.controller.UnitController.listen()");
                return services.getUnit(unit.getId());
            case "createUnit":
                return services.createUnit(unit);
            case "updateUnit":
                return services.updateUnit(unit);
            case "deleteUnit":
                return services.deleteUnit(unit.getId());
            case "removeOneUser":
                return services.removeUserInUnit(unit.getId(), unit.getUserId().get(0));
            case "saveOneUser":
                return services.saveUserInUnit(unit.getId(), unit.getUserId().get(0));
            case "checkAuthority":
                return services.checkAuthor(unit.getId(), unit.getCommanderId());
            default:
                return new Unit();
        }
    }

}
