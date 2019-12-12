package ir.maktab32.java.projects.hw6.scholarshipmanagement.features.dashboard.impl;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.config.DatabaseConfig;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.share.AuthenticationService;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.dashboard.usecases.DashboardUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DashboardUseCaseImpl implements DashboardUseCase {

    public void display() {
        User user = AuthenticationService.getInstance().getLoginUser();
        Map<String, Integer> result = new HashMap<String, Integer>();
        try {
            Connection connection = DatabaseConfig.getDatabaseConnection();
            String sql = "select status, count(id) as countStatus from scholarship group by(status)";
            if (user.getRole().equals("Student"))
                sql = "select status, count(id) as countStatus from scholarship where requesterId = ? group by(status)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (user.getRole().equals("Student"))
                preparedStatement.setLong(1,user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String status = resultSet.getString("status");
                int count = resultSet.getInt("countStatus");
                result.put(status, count);
            }

            System.out.println("===============================================");
            System.out.println("                  Dashboard");
            System.out.println("===============================================");
            System.out.printf("%-20s\t%-5s\t%-30s", "Status", "Count", "Chart");
            System.out.print("\n-----------------------------------------------");
            for (String statusItem : result.keySet()){
                String chart = "";
                for (int i = 1; i <= result.get(statusItem); i++){
                    chart += "\u25ac";
                }
                System.out.println();
                System.out.printf("%-20s\t%-5d\t%-30s", statusItem + ":" , result.get(statusItem), chart);
            }
            System.out.println("\n===============================================\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
