package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.usecases;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.ScholarshipLog;

import java.util.List;

public interface FindScholarshipHistoryUseCase {
    List<ScholarshipLog> listLogs(Long scholarshipId);
}
