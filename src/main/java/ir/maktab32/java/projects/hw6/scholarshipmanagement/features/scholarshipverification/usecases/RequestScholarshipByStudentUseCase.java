package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.usecases;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.UseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.Scholarship;

@UseCase
public interface RequestScholarshipByStudentUseCase {
    void request(Scholarship scholarship);
}
