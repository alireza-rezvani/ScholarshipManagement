package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.impl;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.Service;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.config.DatabaseConfig;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.share.AuthenticationService;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.usecases.FindScholarshipByManagerUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.Scholarship;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindScholarshipByManagerUseCaseImpl implements FindScholarshipByManagerUseCase {
    public List<Scholarship> listScholarships() {
        User user = AuthenticationService.getInstance().getLoginUser();
        List<Scholarship> result = new ArrayList<Scholarship>();
        if (user != null && user.getRole().equals("Manager")){
            try {
                Connection connection = DatabaseConfig.getDatabaseConnection();
                String sql = "select * from scholarship where status = 'AcceptedBySupervisor'";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    Scholarship scholarship = new Scholarship(
                            resultSet.getLong("id"),
                            resultSet.getString("status"),
                            resultSet.getString("name"),
                            resultSet.getString("family"),
                            resultSet.getString("nationalCode"),
                            resultSet.getString("lastUni"),
                            resultSet.getString("lastDegree"),
                            resultSet.getString("lastField"),
                            resultSet.getFloat("lastScore"),
                            resultSet.getString("applyUni"),
                            resultSet.getString("applyDegree"),
                            resultSet.getString("applyField"),
                            resultSet.getString("applyField"),
                            resultSet.getLong("requesterId")
                    );

                    result.add(scholarship);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
