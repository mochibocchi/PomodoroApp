import com.example.pomodoroapp.Controller.TimerController;
import com.example.pomodoroapp.Model.TimerMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimerControllerTest {

    private TimerController controller;

    @BeforeEach
    public void setUp() {
        controller = new TimerController();
    }
    @Test
    public void testPomodoroTimerValue() {
        assertEquals(TimerMode.POMODORO, controller.model.getMode());
    }
    @Test
    public void testPomodoroRunning() {
        controller.initPomodoro();
        assertFalse(controller.isRunning);
    }
    @Test
    public void testPomodoroCheckSeconds() {
        controller.initPomodoro();
        assertEquals(0, controller.model.getSeconds());
    }

    @Test
    public void testShortBreakCheckMinutes() {
        controller.initShortBreak();
        assertEquals(5, controller.model.getMinutes());
    }

    @Test
    public void testShortBreakCheckSeconds() {
        controller.initShortBreak();
        assertEquals(0, controller.model.getSeconds());
    }

    @Test
    public void testShortBreakCheckMode() {
        controller.initShortBreak();
        assertEquals(TimerMode.SHORT_BREAK, controller.model.getMode());
    }

    @Test
    public void testLongBreakRunning() {
        controller.initLongBreak();
        assertFalse(controller.isRunning);
    }

    @Test
    public void testLongBreakCheckMinutes() {
        controller.initLongBreak();
        assertEquals(10, controller.model.getMinutes());
    }

    @Test
    public void testLongBreakCheckSeconds() {
        controller.initLongBreak();
        assertEquals(0, controller.model.getSeconds());
    }

}