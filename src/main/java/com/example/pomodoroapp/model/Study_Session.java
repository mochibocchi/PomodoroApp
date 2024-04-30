package com.example.pomodoroapp.Model;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.value.ObservableValue;

import java.util.List;

public class Study_Session {
    private int accountId;
    private int sessionId;
    private int total_time;
    private String completedWork;

    public Study_Session(int accountId, int total_time, String completedWork) {
        this.accountId = accountId;
        this.total_time = total_time;
        this.completedWork = completedWork;
    }




    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getTotalTime() {
        return total_time;
    }

    public void setTotalTime(int total_time) {
        this.total_time = total_time;
    }

    public String getCompletedWork() {
        return completedWork;
    }

    public void setCompletedWork(String completedWork) {
        this.completedWork = completedWork;
    }

    public int getLoggedInUserId() {
        return accountId;
    }

}