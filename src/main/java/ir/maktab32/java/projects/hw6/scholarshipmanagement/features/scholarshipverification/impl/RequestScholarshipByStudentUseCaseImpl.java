package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.impl;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.Service;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.config.DatabaseConfig;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.share.AuthenticationService;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.impl.SaveLogByApplicationUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.usecases.RequestScholarshipByStudentUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.Scholarship;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class RequestScholarshipByStudentUseCaseImpl implements RequestScholarshipByStudentUseCase {
    public void request(Scholarship scholarship) {
        User user = AuthenticationService.getInstance().getLoginUser();
        if (user != null && user.getRole().equals("Student")){
            try {
                Connection connection = DatabaseConfig.getDatabaseConnection();
                String sql = "insert into " +
                        "scholarship(status, name, family, nationalCode, lastUni, lastDegree, lastField" +
                        ", lastScore, applyUni, applyDegree, applyField, applyDate, requesterId) " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, "RequestedByStudent");
                preparedStatement.setString(2, scholarship.getName());
                preparedStatement.setString(3, scholarship.getFamily());
                preparedStatement.setString(4, scholarship.getNationalCode());
                preparedStatement.setString(5, scholarship.getLastUni());
                preparedStatement.setString(6, scholarship.getLastDegree());
                preparedStatement.setString(7, scholarship.getLastField());
                preparedStatement.setFloat(8, scholarship.getLastScore());
                preparedStatement.setString(9, scholarship.getApplyUni());
                preparedStatement.setString(10, scholarship.getApplyDegree());
                preparedStatement.setString(11, scholarship.getApplyField());
                preparedStatement.setString(12, scholarship.getApplyDate());
                preparedStatement.setLong(13, scholarship.getRequesterId());
                preparedStatement.execute();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                Long createdScholarshipId = null;
                if (resultSet.next())
                    createdScholarshipId = resultSet.getLong(1);

                System.out.println("\t\t\u2705 Your Scholarship Id: " + createdScholarshipId);

                new SaveLogByApplicationUseCaseImpl().save(createdScholarshipId, "RequestedByStudent");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
