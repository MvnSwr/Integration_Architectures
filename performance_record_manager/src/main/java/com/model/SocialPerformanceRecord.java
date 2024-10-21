package com.model;

import org.bson.Document;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// @AllArgsConstructor
public class SocialPerformanceRecord {
    private Integer id;
    private Integer sid;
    private Integer year;
    private Integer leadershipCompetence;
    private Integer opennessToEmployee;
    private Integer socialBehaviourToEmployee;
    private Integer attitudeTowardsClient;
    private Integer communicationSkills;
    private Integer integrityToCompany;
    ///
    public SocialPerformanceRecord(Integer id, Integer year, Integer leadershipCompetence, Integer opennessToEmployee,
                                   Integer socialBehaviourToEmployee, Integer attitudeTowardsClient,
                                   Integer communicationSkills, Integer integrityToCompany) {
        this.id = id;
        this.year = year;
        this.leadershipCompetence = leadershipCompetence;
        this.opennessToEmployee = opennessToEmployee;
        this.socialBehaviourToEmployee = socialBehaviourToEmployee;
        this.attitudeTowardsClient = attitudeTowardsClient;
        this.communicationSkills = communicationSkills;
        this.integrityToCompany = integrityToCompany;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(SalesMan salesMan) {
        this.sid = salesMan.getSid();
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getLeadershipCompetence() {
        return this.leadershipCompetence;
    }

    public void setLeadershipCompetence(Integer leadershipCompetence) {
        this.leadershipCompetence = leadershipCompetence;
    }

    public Integer getOpennessToEmployee() {
        return this.opennessToEmployee;
    }

    public void setOpennessToEmployee(Integer opennessToEmployee) {
        this.opennessToEmployee = opennessToEmployee;
    }

    public Integer getSocialBehaviourToEmployee() {
        return this.socialBehaviourToEmployee;
    }

    public void setSocialBehaviourToEmployee(Integer socialBehaviourToEmployee) {
        this.socialBehaviourToEmployee = socialBehaviourToEmployee;
    }

    public Integer getAttitudeTowardsClient() {
        return attitudeTowardsClient;
    }

    public void setAttitudeTowardsClient(Integer attitudeTowardsClient) {
        this.attitudeTowardsClient = attitudeTowardsClient;
    }

    public Integer getCommunicationSkills() {
        return communicationSkills;
    }

    public void setCommunicationSkills(Integer communicationSkills) {
        this.communicationSkills = communicationSkills;
    }

    public Integer getIntegrityToCompany() {
        return integrityToCompany;
    }

    public void setIntegrityToCompany(Integer integrityToCompany) {
        this.integrityToCompany = integrityToCompany;
    }
    ///
    public Document toDocument() {
        org.bson.Document document = new Document();
        document.append("pid", this.id);
        document.append("year", this.year);
        document.append("leadershipCompetence", this.leadershipCompetence);
        document.append("opennessToEmployee", this.opennessToEmployee);
        document.append("socialBehaviourToEmployee", this.socialBehaviourToEmployee);
        document.append("attitudeTowardsClient", this.attitudeTowardsClient);
        document.append("communicationSkills", this.communicationSkills);
        document.append("integrityToCompany", this.integrityToCompany);

        return document;
    }
}
