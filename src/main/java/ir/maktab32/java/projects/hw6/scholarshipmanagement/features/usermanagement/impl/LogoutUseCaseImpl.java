package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.impl;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.Service;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.share.AuthenticationService;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.usecases.LogoutUseCase;

@Service
public class LogoutUseCaseImpl implements LogoutUseCase {
    public void logout() {
        AuthenticationService.getInstance().setLoginUser(null);
    }
}
