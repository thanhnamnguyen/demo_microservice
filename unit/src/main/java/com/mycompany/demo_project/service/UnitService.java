/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.service;

import com.mycompany.demo_project.data.Unit;
import com.mycompany.demo_project.repo.UnitRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class UnitService {

    @Autowired
    private UnitRepo unitRepo;

    @Autowired
    private MongoOperations mongoOperations;

    public Unit getUnit(String Id) {
        if (Id == null) {
            return new Unit();
        }
        Optional<Unit> u = unitRepo.findById(Id);
        System.out.println(Id);
        return u.isPresent() ? u.get() : new Unit();
    }

    public Unit saveUserInUnit(String unitId, String userId) {
        if (unitId == null || userId == null) {
            return new Unit();
        }
        Optional<Unit> u = unitRepo.findById(unitId);
        if (!u.isPresent()) {
            return new Unit();
        }
        Unit unit = u.get();
        List<String> user = unit.getUserId();
        if (user == null) {
            user = new ArrayList<>();
        }
        user.add(userId);
        unit.setUserId(user);
        return unitRepo.save(unit);
    }

    public Unit removeUserInUnit(String unitId, String userId) {
        if (unitId == null || userId == null) {
            return new Unit();
        }
        Optional<Unit> u = unitRepo.findById(unitId);
        if (!u.isPresent()) {
            return new Unit();
        }
        Unit unit = u.get();
        List<String> user = unit.getUserId();
        if (user == null) {
            user = new ArrayList<>();
        }
        user.remove(userId);
        unit.setUserId(user);
        return unitRepo.save(unit);
    }

    public Unit addUnitChild(String unitId, String parentId) {
        if (unitId == null || parentId == null) {
            return new Unit();
        }
        Optional<Unit> u = unitRepo.findById(parentId);
        Unit parent = u.get();
        if (parent.getChildUnitId() == null) {
            parent.setChildUnitId(new ArrayList<>());
        }
        if (!parent.getChildUnitId().contains(unitId)) {
            parent.getChildUnitId().add(unitId);
        }
        System.out.println("com.mycompany.demo_project.service.UnitService.addUnitChild()");
        return unitRepo.save(parent);
    }

    public Unit removeUnitChild(String unitId, String parentId) {
        if (unitId == null || parentId == null) {
            return new Unit();
        }
        Optional<Unit> u = unitRepo.findById(parentId);
        Unit parent = u.get();
        if (parent.getChildUnitId() == null) {
            return null;
        }
        parent.getChildUnitId().remove(unitId);
        System.out.println("com.mycompany.demo_project.service.UnitService.removeUnitChild()");
        return unitRepo.save(parent);
    }

    //update all unit's info except childunit list and user list, parent (layout)
    public Unit updateUnit(Unit unit) {
        if (unit.getId() == null) {
            return new Unit();
        }
        String unitId = unit.getId();
        Optional<Unit> u = unitRepo.findById(unitId);
        if (!u.isPresent()) {
            return new Unit();
        }

        Unit updated = catUpdate(u.get(), unit);
        return unitRepo.save(updated);

    }

    public Unit createUnit(Unit unit) {
        if (unit.getParentUnitId() == null) {
            return new Unit();
        }
        String parentUnitId = unit.getParentUnitId();
        if (!unitRepo.existsById(parentUnitId)) {
            return new Unit();//error - parent unit required
        }
        Unit inserted = unitRepo.insert(unit);
        addUnitChild(inserted.getId(), parentUnitId);
        return inserted;
    }

    public Unit deleteUnit(String unitId) {
        if (unitId == null) {
            return new Unit();
        }
        Optional<Unit> u = unitRepo.findById(unitId);
        if (!u.isPresent()) {
            return new Unit();
        }
        //change unit layout
        removeUnitChild(unitId, u.get().getParentUnitId());
        for (String childId : u.get().getChildUnitId()) {
            Optional<Unit> child = unitRepo.findById(childId);
            if (child.isPresent()) {
                child.get().setParentUnitId(null);
                unitRepo.save(child.get());
            }
        }
        unitRepo.deleteById(unitId);
        return u.get();
    }

    public Unit checkAuthor(String UnitId, String userId) {
        if (userId == null) {
            return new Unit();
        }

        Optional<Unit> u;
        String Id = UnitId;

        while (Id != null) {
            u = unitRepo.findById(Id);
            if (u.isPresent()) {
                if (userId.equalsIgnoreCase(u.get().getCommanderId())) {
                    return u.get();
                }
                Id = u.get().getParentUnitId();
            } else {
                break;
            }
        }
        return new Unit();
    }

    public Unit catUpdate(Unit current, Unit update) {
        //updateUnit only contain what change
        if (update.getUnitName() != null) {
            current.setUnitName(update.getUnitName());
        }

        if (update.getCommanderId() != null) {
            current.setCommanderId(update.getCommanderId());
        }

        if (update.getParentUnitId() != null && !update.getParentUnitId().equalsIgnoreCase(current.getParentUnitId())) {
            addUnitChild(current.getId(), update.getParentUnitId());
            removeUnitChild(current.getId(), current.getParentUnitId());
            current.setParentUnitId(update.getParentUnitId());
            System.out.println("com.mycompany.demo_project.service.UnitService.catUpdate()");
        }
        return current;
    }
}
