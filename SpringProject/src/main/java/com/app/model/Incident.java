package com.app.model;

import com.app.enums.IncidentStatus;
import com.app.enums.IncidentType;

public class Incident {

    private int id;
    private IncidentType incidentType;
    private String progressDetails;
    private IncidentStatus incidentStatus;

    public Incident() {

    }

    public Incident(IncidentType incidentType, String progressDetails, IncidentStatus incidentStatus) {

        this.incidentType = incidentType;
        this.progressDetails = progressDetails;
        this.incidentStatus = incidentStatus;
    }
    public Incident(int id, IncidentType incidentType, String progressDetails, IncidentStatus incidentStatus) {
        this.id = id;
        this.incidentType = incidentType;
        this.progressDetails = progressDetails;
        this.incidentStatus = incidentStatus;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IncidentType getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(IncidentType incidentType) {
        this.incidentType = incidentType;
    }

    public String getProgressDetails() {
        return progressDetails;
    }

    public void setProgressDetails(String progressDetails) {
        this.progressDetails = progressDetails;
    }

    public IncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", incidentType=" + incidentType +
                ", progressDetails='" + progressDetails + '\'' +
                ", incidentStatus=" + incidentStatus +
                '}';
    }
}
