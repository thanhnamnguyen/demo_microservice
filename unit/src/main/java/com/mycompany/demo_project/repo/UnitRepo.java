/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.repo;

import com.mycompany.demo_project.data.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Administrator
 */
public interface UnitRepo extends MongoRepository<Unit, String> {

//    List<Unit> findByName(String name);
//
//    List<Unit> findByNameLikeOrderByNameDesc(String regexp);
//
//    List<Unit> findAllByOrderByNameDesc();
//
//    List<Unit> findByNameEndingWith(String regexp);
}
