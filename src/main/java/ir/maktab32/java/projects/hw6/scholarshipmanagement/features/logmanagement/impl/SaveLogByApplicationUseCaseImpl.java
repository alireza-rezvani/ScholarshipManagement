package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.impl;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.annotations.Service;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.config.DatabaseConfig;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.share.AuthenticationService;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.usecases.SaveLogByApplicationUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SaveLogByApplicationUseCaseImpl implements SaveLogByApplicationUseCase {
    public void save(Long scholarshipId, String action) {
        User user = AuthenticationService.getInstance().getLoginUser();

        if (user != null){
            Long currentTimeMillis = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
            java.util.Date date = new Date(currentTimeMillis);
            String stringDate = sdf.format(date);
            try {
                Connection connection = DatabaseConfig.getDatabaseConnection();
                String sql = "insert into scholarshipLog(action, userId, userRole, date, scholarshipId) " +
                        "values(?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, action);
                preparedStatement.setLong(2, user.getId());
                preparedStatement.setString(3, user.getRole());
                preparedStatement.setString(4, stringDate);
                preparedStatement.setLong(5, scholarshipId);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
