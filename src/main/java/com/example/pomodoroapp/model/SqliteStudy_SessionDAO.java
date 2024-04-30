package com.example.pomodoroapp.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SqliteStudy_SessionDAO implements IStudy_SessionDAO{
    private Connection connection;

    public SqliteStudy_SessionDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    public static void addSession(Study_Session studySession) {
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS study_sessions ("
                    + "accountId INTEGER PRIMARY KEY,"
                    + "sessionId INTEGER NOT NULL,"
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
    public void addStudy_Session(Study_Session study_session) {
        try {
            int accountId = getAccountId();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO study_sessions (accountId, sessionId, total_time, completedWork) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, accountId);
            statement.setInt(2, study_session.getSessionId());
            statement.setInt(3, study_session.getTotalTime());
            statement.setString(4, study_session.getCompletedWork());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    study_session.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Study_Session getStudy_Session(int accountId) {
        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM study_sessions WHERE accountId = ?");
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int sessionId = resultSet.getInt("sessionId");
                int totalTime = resultSet.getInt("total_time");
                String completedWork = resultSet.getString("completedWork");

                return new Study_Session(accountId, sessionId, totalTime, completedWork);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<Study_Session> getStudy_Sessions() {
        List<Study_Session> study_sessions = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM study_sessions";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int accountId = resultSet.getInt("accountId");
                int sessionId = resultSet.getInt("sessionId");
                int totalTime = resultSet.getInt("total_time");
                String completedWork = resultSet.getString("completedWork");
            }


        } catch (SQLException e) {
             e.printStackTrace();
        }
        return study_sessions;
    }

    public int getAccountId() {
        int accountId = -1; // Default value if not found

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
