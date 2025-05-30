package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

    public Account insertAccount(Account account) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "insert into Account (username, password) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                return new Account(id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Account getAccountByUsername(String username) {

        try {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "select * from Account where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("account_id");
                String foundUsername = resultSet.getString("username");
                String password = resultSet.getString("password");

                return new Account(id, foundUsername, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
