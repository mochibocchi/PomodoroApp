package com.example.pomodoroapp.Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteAccountDAO implements IAccountDAO {
    private Connection connection;

    public SqliteAccountDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS accounts ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "firstName VARCHAR NOT NULL,"
                    + "lastName VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAccount(Account account) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts (firstName, lastName, email, password) VALUES (?, ?, ?, ?)");
            statement.setString(1, account.getFirstName());
            statement.setString(2, account.getLastName());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getPassword());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                account.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET firstName = ?, lastName = ?, email = ?, password = ? WHERE id = ?");
            statement.setString(1, account.getFirstName());
            statement.setString(2, account.getLastName());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getPassword());
            statement.setInt(5, account.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(Account account) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM accounts WHERE id = ?");
            statement.setInt(1, account.getId());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Account getAccountId(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Account account = new Account(firstName, lastName, email, password);
                account.setId(id);
                return account;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM accounts";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = ((ResultSet) resultSet).getString("email");
                String password = resultSet.getString("password");
                Account account = new Account(firstName, lastName, email, password);
                account.setId(id);
                accounts.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public boolean login(String email, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getLoggedInUserId(String email, String password) {
        int userId = -1;

        try {
            String query = "SELECT id FROM accounts WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
                System.out.println("Logged-in user ID: " + userId);
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }


}
