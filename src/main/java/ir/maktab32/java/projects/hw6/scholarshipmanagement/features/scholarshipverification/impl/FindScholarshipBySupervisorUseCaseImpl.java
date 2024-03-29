package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.impl;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.Service;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.config.DatabaseConfig;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.share.AuthenticationService;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.usecases.FindScholarshipBySupervisorUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.Scholarship;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindScholarshipBySupervisorUseCaseImpl implements FindScholarshipBySupervisorUseCase {

    public List<Scholarship> listScholarships() {
        User loginUser = AuthenticationService.getInstance().getLoginUser();
        List<Scholarship> result = new ArrayList<Scholarship>();
        if (loginUser != null) {
            if (loginUser.getRole().equals("Supervisor")) {
                // connection
                Connection connection = null;
                try {
                    connection = DatabaseConfig.getDatabaseConnection();
                    // query
                    String sql = "select * from scholarship where status = 'RequestedByStudent' or status = 'RejectedByManager'";
                    // result
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
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
                                resultSet.getString("applyDate"),
                                resultSet.getLong("requesterId")
                        );
                        result.add(scholarship);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}

