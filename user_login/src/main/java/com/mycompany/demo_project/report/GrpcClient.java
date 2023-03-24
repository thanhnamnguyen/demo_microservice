/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.report;

import com.mycompany.demo_project.model.Delete;
import com.mycompany.demo_project.model.EditReport;
import com.mycompany.demo_project.model.Empty;
import com.mycompany.demo_project.model.FreshReport;
import com.mycompany.demo_project.model.PTPRequest;
import com.mycompany.demo_project.model.ReportDetail;
import com.mycompany.demo_project.model.ReportInfo;
import com.mycompany.demo_project.model.ReportList;
import com.mycompany.demo_project.model.ReportServiceGrpc;
import com.mycompany.demo_project.model.UnitInfo;
import com.mycompany.demo_project.userRepo.User;
import com.mycompany.demo_project.userRepo.UserRepo;
import java.util.List;
import java.util.Optional;
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
public class GrpcClient {

    @Autowired
    UserRepo repo;

    User user = null;

    @Autowired
    private ReportServiceGrpc.ReportServiceBlockingStub blockingStub;

    public UserSension getUser() throws Exception {
        String jwt;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            jwt = authentication.getName();
        } else {
            throw new Exception("please login");
        }
        Optional<User> u = repo.findById(jwt);
        user = u.get();
        return new UserSension(jwt);
    }

    public List<Report> getAllSendReport() throws Exception {
        UserSension us = getUser();
        ReportList list = blockingStub
                .withCallCredentials(us)
                .getAllSendReport(Empty.newBuilder().build());

        return Report.ProtoTranform(list.getReportList());
    }

    public List<Report> getAllReportReceive() throws Exception {
        UserSension us = getUser();
        ReportList list = blockingStub
                .withCallCredentials(us)
                .getAllReportReceive(UnitInfo.newBuilder().setUnitId(user.getUnitId()).build());
        return Report.ProtoTranform(list.getReportList());
    }

    public List<Report> getAllReportFrom(String userId) throws Exception {
        UserSension us = getUser();
        PTPRequest info = PTPRequest.newBuilder()
                .setSendUserId(userId)
                .setReceiveUnitId(user.getUnitId())
                .build();

        ReportList list = blockingStub
                .withCallCredentials(us)
                .getAllReportToUnitFrom(info);
        return Report.ProtoTranform(list.getReportList());
    }

    public Report approveReport(String reportId) throws Exception {
        UserSension us = getUser();
        ReportInfo info = ReportInfo.newBuilder()
                .setReportId(reportId)
                .build();
        ReportDetail re = blockingStub
                .withCallCredentials(us)
                .approveReport(info);
        return new Report(re);
    }

    public Report editReport(Report r) throws Exception {
        UserSension us = getUser();
        EditReport er = EditReport.newBuilder()
                .setId(r.getId())
                .setContext(r.getContext())
                .setTopic(r.getTopic())
                .setUnitId(r.getUnitId()).build();

        ReportDetail re = blockingStub
                .withCallCredentials(us)
                .editReport(er);
        return new Report(re);
    }

    public boolean deleteReport(String reportId) throws Exception {
        UserSension us = getUser();
        ReportInfo info = ReportInfo.newBuilder()
                .setReportId(reportId)
                .build();
        Delete d = blockingStub
                .withCallCredentials(us)
                .deleteReport(info);
        return d.getIsDeleted();
    }

    public Report createReport(Report request) throws Exception {
        UserSension us = getUser();
        FreshReport fresh = FreshReport.newBuilder()
                .setUnitId(request.getUnitId())
                .setTopic(request.getTopic())
                .setContext(request.getContext())
                .build();
        ReportDetail re = blockingStub
                .withCallCredentials(us)
                .createReport(fresh);
        return new Report(re);
    }

}
