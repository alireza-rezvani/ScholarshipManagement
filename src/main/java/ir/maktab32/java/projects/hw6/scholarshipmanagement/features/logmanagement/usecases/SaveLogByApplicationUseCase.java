package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.usecases;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.UseCase;

@UseCase
public interface SaveLogByApplicationUseCase {
    void save(Long scholarshipId, String action);
}
