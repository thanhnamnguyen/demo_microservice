/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.loginRepo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author Administrator
 */
@Configuration
@EnableWebSecurity
public class SecureConfg extends WebSecurityConfigurerAdapter {

    //use noop in password instead for no encoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new ..encoder();
//    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable() //should find another way
                .authorizeRequests()
                .antMatchers("/regis").permitAll()
                .anyRequest().permitAll();
        //.authenticated() // Tất cả các request khác đều cần phải xác thực mới được truy cập
        //                .and()
        //                .formLogin() // Cho phép người dùng xác thực bằng form login
        //                .defaultSuccessUrl("/test1")
        //                .permitAll() // Tất cả đều được truy cập vào địa chỉ này
        //                .and()
        //                .logout() // Cho phép logout
        //                .permitAll()
        //                .and()
        //                .rememberMe()
        //                .key("uniqueAndSecret")
        //                .alwaysRemember(true)
        //                .and()
        //                .httpBasic();
    }

}
