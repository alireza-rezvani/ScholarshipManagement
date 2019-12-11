package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.impl;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.Service;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.config.DatabaseConfig;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.share.AuthenticationService;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.impl.SaveLogByApplicationUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.usecases.AcceptScholarshipByManagerUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class AcceptScholarshipByManagerUseCaseImpl implements AcceptScholarshipByManagerUseCase {
    public void accept(Long scholarshipId) {
        User user = AuthenticationService.getInstance().getLoginUser();
        if (user != null && user.getRole().equals("Manager")){
            try {
                Connection connection = DatabaseConfig.getDatabaseConnection();
                String sql = "update scholarship set status = 'AcceptedByManager' where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, scholarshipId);
                preparedStatement.executeUpdate();

                new SaveLogByApplicationUseCaseImpl().save(scholarshipId, "AcceptedByManager");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
