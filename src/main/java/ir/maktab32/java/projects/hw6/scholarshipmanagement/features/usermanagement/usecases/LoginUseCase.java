package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.usecases;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.UseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.User;

@UseCase
public interface LoginUseCase {
    User login(String username, String password);
}
