package ir.maktab32.java.projects.hw6.scholarshipmanagement.model;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.Entity;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.Id;
@Entity
public class ScholarshipLog {

    @Id
    Long id;
    String action;
    @Id
    Long userId;
    String userRole;
    String date;
    @Id
    Long scholarshipId;

    public ScholarshipLog(Long id, String action, Long userId, String userRole, String date, Long scholarshipId) {
        this.id = id;
        this.action = action;
        this.userId = userId;
        this.userRole = userRole;
        this.date = date;
        this.scholarshipId = scholarshipId;
    }

    @Override
    public String toString() {

        return String.format("%-6d\t%-13d\t%-22s\t%-6d\t%-12s\t%12s"
        ,id, scholarshipId, action, userId, userRole, date);

    }
}
