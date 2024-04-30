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
    public void testInitialValues() {
        assertEquals(25, controller.model.getMinutes());
        assertEquals(0, controller.model.getSeconds());
        assertEquals(TimerMode.POMODORO, controller.model.getMode());
        assertFalse(controller.isRunning);
    }
}