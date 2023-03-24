/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.repo;

import com.mycompany.demo_project.data.Report;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Administrator
 */
public interface ReportRepo extends MongoRepository<Report, String> {

    List<Report> findAllByOwnerId(String id);

    List<Report> findAllByUnitId(String id);

    List<Report> findAllByOwnerIdAndUnitId(String ownerId, String unitId);
}
