package com.mycompany.demo_project.data;

import com.mycompany.demo_project.model.FreshReport;
import com.mycompany.demo_project.model.ReportDetail;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("report")
public class Report {

    @Id
    private String id;
    private String ownerId;
    private String topic;
    private String context;
    private String unitId;
    private boolean approved;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the ownerId
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return the topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @param topic the topic to set
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * @return the approved
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * @param approved the approved to set
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Report() {

    }

    public Report(ReportDetail x) {
        this.setId(x.getId());
        this.setOwnerId(x.getOwnerId());
        this.setTopic(x.getTopic());
        this.setContext(x.getContext());
        this.setUnitId(x.getUnitId());
        this.setApproved(x.getApproved());
    }

    public Report(FreshReport x) {
        this.setTopic(x.getTopic());
        this.setContext(x.getContext());
        this.setUnitId(x.getUnitId());
    }

    public ReportDetail MessageTranform() {
        ReportDetail re = ReportDetail.newBuilder()
                .setId(this.getId())
                .setOwnerId(this.getOwnerId())
                .setTopic(this.getTopic())
                .setContext(this.getContext())
                .setApproved(this.isApproved())
                .setUnitId(this.getUnitId())
                .build();
        return re;
    }

    public static List<Report> ProtoTranform(List<ReportDetail> list) {
        List<Report> re = new ArrayList<>();
        for (ReportDetail x : list) {
            re.add(new Report(x));
        }
        return re;
    }

    public static List<ReportDetail> ListTranform(List<Report> list) {
        List<ReportDetail> re = new ArrayList<>();
        for (Report x : list) {
            re.add(x.MessageTranform());
        }
        return re;
    }

    /**
     * @return the unitId
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * @param unitId the unitId to set
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
