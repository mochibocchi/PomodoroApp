package com.example.pomodoroapp.Model;
/**
 * The TimerModel class represents a timer used in a Pomodoro application.
 * Includes the timer's minutes, seconds, and the mode (Pomodoro, Short Break, Long Break).
 */
public class TimerModel {
/**
 * Constructs a TimerModel with the specified starting minute, second and mode.
 */

    private int minutes;
    private int seconds;
    private TimerMode mode;


    public TimerModel() {
        this.minutes = 25;
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