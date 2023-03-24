/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_project.grpc_server;

import com.mycompany.demo_project.data.Report;
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
import com.mycompany.demo_project.repo.ReportRepo;
import com.mycompany.demo_project.unit.UnitService;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class ReportService extends ReportServiceGrpc.ReportServiceImplBase {

    @Autowired
    ReportRepo repo;

    @Autowired
    AuthInc auth;

    @Autowired
    UnitService service;

    @Override
    public void getAllSendReport(Empty request, StreamObserver<ReportList> responseObserver) {
        List<Report> list = repo.findAllByOwnerId(auth.userId);
        ReportList rl = ReportList.newBuilder().addAllReport(Report.ListTranform(list)).build();
        responseObserver.onNext(rl);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllReportReceive(UnitInfo request, StreamObserver<ReportList> responseObserver) {
        if (!service.checkAuthor(auth.userId, request.getUnitId())) {
            //error
        }
        List<Report> list = repo.findAllByUnitId(auth.userId);
        ReportList rl = ReportList.newBuilder().addAllReport(Report.ListTranform(list)).build();
        responseObserver.onNext(rl);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllReportToUnitFrom(PTPRequest request, StreamObserver<ReportList> responseObserver) {
        if (!service.checkAuthor(auth.userId, request.getReceiveUnitId())) {
            //error
        }
        List<Report> list = repo.findAllByOwnerIdAndUnitId(request.getSendUserId(), request.getReceiveUnitId());
        ReportList rl = ReportList.newBuilder().addAllReport(Report.ListTranform(list)).build();
        responseObserver.onNext(rl);
        responseObserver.onCompleted();
    }

    @Override
    public void approveReport(ReportInfo request, StreamObserver<ReportDetail> responseObserver) {
        Optional<Report> r = repo.findById(request.getReportId());
        if (!r.isPresent()) {
            //error
        }
        Report report = r.get();
        if (!service.checkAuthor(auth.userId, report.getUnitId())) {
            //error
        }
        report.setApproved(true);
        repo.save(report);

        responseObserver.onNext(report.MessageTranform());
        responseObserver.onCompleted();
    }

    @Override
    public void editReport(EditReport request, StreamObserver<ReportDetail> responseObserver) {
        Optional<Report> r = repo.findById(request.getId());
        if (!r.isPresent()) {
            //error
        }
        Report report = r.get();
        if (!report.getOwnerId().equals(auth.userId)) {
            //error
        }
        report.setContext(request.getContext());
        report.setTopic(request.getTopic());
        report.setUnitId(request.getUnitId());
        repo.save(report);

        responseObserver.onNext(report.MessageTranform());
        responseObserver.onCompleted();
    }

    public void deleteReport(ReportInfo request, StreamObserver<Delete> responseObserver) {
        Optional<Report> r = repo.findById(request.getReportId());
        if (!r.isPresent()) {
            //error
        }
        Report report = r.get();
        if (!report.getOwnerId().equals(auth.userId)) {
            //error
        }
        repo.deleteById(report.getId());
        Delete del = Delete.newBuilder()
                .setIsDeleted(true)
                .build();
        responseObserver.onNext(del);
        responseObserver.onCompleted();
    }

    public void createReport(FreshReport request, StreamObserver<ReportDetail> responseObserver) {
        Report report = new Report(request);
        report.setOwnerId(auth.userId);
        report.setApproved(false);
        Report re = repo.insert(report);
        responseObserver.onNext(re.MessageTranform());
        responseObserver.onCompleted();
    }
}
