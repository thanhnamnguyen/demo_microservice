/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.userRepo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Administrator
 */
public interface UserRepo extends MongoRepository<User, String> {

    List<User> findByName(String name);

    List<User> findByNameLikeOrderByNameDesc(String regexp);

    List<User> findAllByOrderByNameDesc();

    List<User> findByNameEndingWith(String regexp);
}
