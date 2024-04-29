package com.example.pomodoroapp.Model;
public class TimerModel {
    private int minutes;
    private int seconds;
    private TimerMode mode;


    public TimerModel() {
        this.minutes = 15;
        this.seconds = 0;
        this.mode = TimerMode.POMODORO;
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

    public TimerMode getMode() { return mode; }

    public void setMode(TimerMode mode) { this.mode = mode; }
}