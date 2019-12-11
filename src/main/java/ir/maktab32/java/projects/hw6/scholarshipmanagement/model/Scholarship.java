package ir.maktab32.java.projects.hw6.scholarshipmanagement.model;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.Entity;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.Id;

@Entity
public class Scholarship {
    @Id
    private Long id;
    // state machine
    private String status;
    // main info
    private String name;
    private String family;
    private String nationalCode;
    // history
    private String lastUni;
    private String lastDegree;
    private String lastField;
    private Float lastScore;
    // apply
    private String applyUni;
    private String applyDegree;
    private String applyField;
    private String applyDate;

    public Scholarship(Long id, String status, String name, String family, String nationalCode, String lastUni, String lastDegree, String lastField, Float lastScore, String applyUni, String applyDegree, String applyField, String applyDate) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.family = family;
        this.nationalCode = nationalCode;
        this.lastUni = lastUni;
        this.lastDegree = lastDegree;
        this.lastField = lastField;
        this.lastScore = lastScore;
        this.applyUni = applyUni;
        this.applyDegree = applyDegree;
        this.applyField = applyField;
        this.applyDate = applyDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getLastUni() {
        return lastUni;
    }

    public void setLastUni(String lastUni) {
        this.lastUni = lastUni;
    }

    public String getLastDegree() {
        return lastDegree;
    }

    public void setLastDegree(String lastDegree) {
        this.lastDegree = lastDegree;
    }

    public String getLastField() {
        return lastField;
    }

    public void setLastField(String lastField) {
        this.lastField = lastField;
    }

    public Float getLastScore() {
        return lastScore;
    }

    public void setLastScore(Float lastScore) {
        this.lastScore = lastScore;
    }

    public String getApplyUni() {
        return applyUni;
    }

    public void setApplyUni(String applyUni) {
        this.applyUni = applyUni;
    }

    public String getApplyDegree() {
        return applyDegree;
    }

    public void setApplyDegree(String applyDegree) {
        this.applyDegree = applyDegree;
    }

    public String getApplyField() {
        return applyField;
    }

    public void setApplyField(String applyField) {
        this.applyField = applyField;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    @Override
    public String toString() {

        return String.format("%-3d %-22s %-10s %-10s  %-12s  %-15s %-10s %-20s  %-9.2f  %-15s  %-11s  %-20s %-10s"
        , id, status, name, family, nationalCode, lastUni, lastDegree, lastField, lastScore
        ,applyUni, applyDegree, applyField, applyDate);

    }
}
