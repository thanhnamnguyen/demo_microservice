/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.loginRepo;

import com.mycompany.demo_project.userRepo.User;
import com.mycompany.demo_project.userRepo.UserRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
@Primary
public class ClientDB implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String id) {
        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(id);
        }
        return new ClientDetail(user.get());
    }
}
