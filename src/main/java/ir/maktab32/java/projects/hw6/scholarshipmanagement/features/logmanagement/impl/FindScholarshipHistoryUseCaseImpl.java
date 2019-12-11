package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.impl;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.config.DatabaseConfig;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.usecases.FindScholarshipHistoryUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.ScholarshipLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindScholarshipHistoryUseCaseImpl implements FindScholarshipHistoryUseCase {
    public List<ScholarshipLog> listLogs(Long scholarshipId) {
        List<ScholarshipLog> scholarshipLogs = new ArrayList<ScholarshipLog>();
        try {
            Connection connection = DatabaseConfig.getDatabaseConnection();
            String sql = "select * from scholarshiplog where scholarshipId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, scholarshipId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                String action = resultSet.getString("action");
                Long userId = resultSet.getLong("userId");
                String userRole = resultSet.getString("userRole");
                String date = resultSet.getString("date");
                scholarshipLogs.add(new ScholarshipLog(id,action,userId,userRole,date,scholarshipId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return scholarshipLogs;
    }
}
