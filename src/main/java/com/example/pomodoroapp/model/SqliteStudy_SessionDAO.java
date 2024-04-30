package com.example.pomodoroapp.Model;

import com.example.pomodoroapp.Controller.LoginController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SqliteStudy_SessionDAO implements IStudy_SessionDAO{

    private LoginController loginController;
    private int accountId;



    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    private Connection connection;

    public SqliteStudy_SessionDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    @Override
    public List<Study_Session> getStudySessions(int accountId) {
        List<Study_Session> sessions = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM study_sessions WHERE accountId = ?");
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int sessionId = resultSet.getInt("sessionId");
                int total_time = resultSet.getInt("total_time");
                String completedWork = resultSet.getString("completedWork");
                Study_Session session = new Study_Session(accountId, total_time, completedWork);
                session.setSessionId(sessionId);
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }



    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS study_sessions ("
                    + "sessionId INTEGER PRIMARY KEY,"
                    + "accountId INTEGER NOT NULL,"
                    + "total_time INTEGER NOT NULL,"
                    + "completedWork TEXT NOT NULL,"
                    + "FOREIGN KEY (accountId) REFERENCES accounts(id)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    @Override
    public void addStudy_Session( Study_Session study_session) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO study_sessions ( accountId, total_time, completedWork) VALUES (?, ?, ?)");
            statement.setInt(1, study_session.getLoggedInUserId());
            statement.setInt(2, AccountData.getInstance().getTotalTimeElapsed());
            statement.setString(3, study_session.getCompletedWork());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 1) {
                study_session.setSessionId(study_session.getSessionId());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public int getAccountId() {
        int accountId = -1;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM accounts WHERE email = '?'");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                accountId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountId;
    }
}
