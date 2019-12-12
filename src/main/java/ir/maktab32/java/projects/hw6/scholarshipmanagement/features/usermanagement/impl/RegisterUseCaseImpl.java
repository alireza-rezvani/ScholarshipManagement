package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.impl;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.config.DatabaseConfig;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.share.AuthenticationService;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.usecases.RegisterUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterUseCaseImpl implements RegisterUseCase {
    public User register(User user) {
        try {
            Connection connection = DatabaseConfig.getDatabaseConnection();
            String sql = "insert into user(username, password, role) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3,user.getRole());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            Long createdUserId = null;
            while (resultSet.next()) {
                createdUserId = resultSet.getLong(1);
            }
            User returnUser = null;
            for (User i : new FindAllUsersUseCaseImpl().getUsersList()){
                if (i.getId() == createdUserId)
                    returnUser = i;
            }
            return returnUser;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
