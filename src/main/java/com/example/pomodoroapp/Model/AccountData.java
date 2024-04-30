package com.example.pomodoroapp.Model;

public class AccountData {
    private static AccountData instance;
    private int accountId;
    private String totalTime;

    private AccountData() {}

    public static AccountData getInstance() {
        if (instance == null) {
            instance = new AccountData();
        }
        return instance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    private int totalTimeElapsed;

    public int getTotalTimeElapsed() {
        return totalTimeElapsed;
    }

    public void addToTotalTimeElapsed(int totalSecondsElapsed) {
        this.totalTimeElapsed = totalSecondsElapsed;
    }
}
