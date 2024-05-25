package com.example.pomodoroapp.Model;

import com.example.pomodoroapp.Controller.LoginController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteTasksDAO  implements  ITasksDAO{
    private int accountId;
    private int taskId;



    private Connection connection;

    public SqliteTasksDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    /**
     * @param accountId the account id of the logged-in user
     * @return the tasks from the db for the account
     */
    @Override
    public List<Study_Tasks> getTasks(int accountId) {
        /**
         * Returns the tasks that the user has created
         */
        List<Study_Tasks> tasks = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tasks WHERE accountId = ?");
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int taskId = resultSet.getInt("taskId");
                String taskDetails = resultSet.getString("taskDetails");
                Study_Tasks task = new Study_Tasks(accountId, taskDetails);
                task.setTaskId(taskId);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }



    private void createTable() {
        /**
         * Creates the table of study tasks
         */
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS tasks ("
                    + "taskId INTEGER PRIMARY KEY,"
                    + "accountId INTEGER NOT NULL,"
                    + "taskDetails TEXT NOT NULL,"
                    + "FOREIGN KEY (accountId) REFERENCES accounts(id)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * @param study_tasks adds a study task to the db
     */
    @Override
    public void addTask(Study_Tasks study_tasks) {
        /**
         * Adds a task for the user who is logged in
         */
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tasks ( accountId, taskDetails) VALUES (?, ?)");
            statement.setInt(1, study_tasks.getLoggedInUserId());
            statement.setString(2, study_tasks.getTaskDetails());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 1) {
                study_tasks.setTaskId(study_tasks.getTaskId());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param accountId the account id that is logged in
     * @param taskDetails the string of the inputted task
     */
    public void deleteTask(int accountId, String taskDetails) {
        /**
         * Deletes the selected task from the database
         */
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM tasks WHERE accountId = ? AND taskDetails = ?");
            statement.setInt(1, accountId);
            statement.setString(2, taskDetails);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
