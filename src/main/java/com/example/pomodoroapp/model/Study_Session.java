package com.example.pomodoroapp.Model;

public class Study_Session {
    private int accountId;
    private int sessionId;
    private int total_time;
    private String completedWork;
    private long session_date;

    public Study_Session(int accountId, int total_time, long session_date, String completedWork) {
        this.accountId = accountId;
        this.total_time = total_time;
        this.session_date = session_date;
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


    public long getSessionDate() {
        return session_date;
    }
}