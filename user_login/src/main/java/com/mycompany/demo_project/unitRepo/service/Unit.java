package com.mycompany.demo_project.unitRepo.service;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("unit")
public class Unit implements Serializable {

    @Id
    private String id;
    private String unitName;
    private String commanderId;
    private String parentUnitId;
    private List<String> childUnitId;
    private List<String> userId;

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
     * @return the unitName
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * @param unitName the unitName to set
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * @return the commanderId
     */
    public String getCommanderId() {
        return commanderId;
    }

    /**
     * @param commanderId the commanderId to set
     */
    public void setCommanderId(String commanderId) {
        this.commanderId = commanderId;
    }

    /**
     * @return the parentUnitId
     */
    public String getParentUnitId() {
        return parentUnitId;
    }

    /**
     * @param parentUnitId the parentUnitId to set
     */
    public void setParentUnitId(String parentUnitId) {
        this.parentUnitId = parentUnitId;
    }

    /**
     * @return the childUnitId
     */
    public List<String> getChildUnitId() {
        return childUnitId;
    }

    /**
     * @param childUnitId the childUnitId to set
     */
    public void setChildUnitId(List<String> childUnitId) {
        this.childUnitId = childUnitId;
    }

    /**
     * @return the userId
     */
    public List<String> getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(List<String> userId) {
        this.userId = userId;
    }

}
