package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.usecases;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.UseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.Scholarship;

import java.util.List;

@UseCase
public interface FindScholarshipByStudentUseCase {
    List<Scholarship> listScholarships();
}
