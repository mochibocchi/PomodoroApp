package com.example.pomodoroapp.Model;
public class TimerModel {
    private int minutes;
    private int seconds;

    public TimerModel() {
        this.minutes = 0;
        this.seconds = 2;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}