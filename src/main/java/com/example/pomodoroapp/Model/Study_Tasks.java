package com.example.pomodoroapp.Model;

public class Study_Tasks {
    private int accountId;
    private String taskDetails;
    private int taskId;


    public Study_Tasks(int accountId, String taskDetails) {
        this.accountId = accountId;
        this.taskDetails = taskDetails;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public int getLoggedInUserId() {
        return accountId;
    }
    public String getTaskDetails() {
        return taskDetails;
    }

}
