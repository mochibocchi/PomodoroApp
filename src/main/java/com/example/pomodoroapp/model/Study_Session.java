package com.example.pomodoroapp.Model;

public class Study_Session {
    private int accountId;
    private int sessionId;
    private int total_time;
    private String completedWork;
    private long session_date;
    private int mood;

    public Study_Session(int accountId, int total_time, long session_date, String completedWork, int mood) {
        this.accountId = accountId;
        this.total_time = total_time;
        this.session_date = session_date;
        this.completedWork = completedWork;
        this.mood = mood;
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

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public static int getMood(String mood) {
        switch (mood) {
            case "Very Bad": return 1;
            case "Bad": return 2;
            case "Poor": return 3;
            case "Below Average": return 4;
            case "Average": return 5;
            case "Above Average": return 6;
            case "Good": return 7;
            case "Very Good": return 8;
            case "Excellent": return 9;
            case "Amazing": return 10;
            default: return 5;
        }
    }
}