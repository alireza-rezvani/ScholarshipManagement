package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.usecases;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.UseCase;

@UseCase
public interface AcceptScholarshipBySupervisorUseCase {
    void accept(Long scholarshipId);
}
