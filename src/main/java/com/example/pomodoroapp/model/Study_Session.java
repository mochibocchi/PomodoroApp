
package com.example.pomodoroapp.model;

public class Study_Session {
    private int accountId;
    private int sessionId;
    private int total_time;
    private String completedWork;

    public Study_Session(int accountId, int total_time, int sessionId, String completedWork) {
        this.accountId = accountId;
        this.total_time = total_time;
        this.sessionId = sessionId;
        this.completedWork = completedWork;
    }

    public int getAccountId() {

        return accountId;
    }

    public void setId(int id) {
        this.accountId = accountId;
    }

    public int getTotalTime() {
        return total_time;
    }

    public void setTotalTime(int total_time) {
        this.total_time = total_time;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getCompletedWork() {
        return completedWork;
    }

    public void setCompletedWork(String completedWork) {
        this.completedWork = completedWork;
    }
}
