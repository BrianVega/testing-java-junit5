package guru.springframework.sfgpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class IndexControllerTest {

    IndexController indexController;

    @BeforeEach
    void setUp() {
        indexController = new IndexController();
    }

    @DisplayName("Test proper View name is returned for index page")
    @Test
    void index() {
        assertEquals("index", indexController.index());
        assertEquals("index", indexController.index(), "Wrong view Returned");
        assertEquals("index", indexController.index(), "Another Expensive Message " +
                "make me only if you have to");
    }

    @Test
    @DisplayName("Test exception")
    void oupsHandler() {
        // If exception is not threw, test will not pass
        assertThrows(ValueNotFoundException.class, () -> {
           indexController.oupsHandler();
        });
    }

    @Test
    void testTimeOut() {
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(2000);
                    System.out.println("I got here");
                });
    }

    @Test
    void testTimeOutPrempt() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(150);
            System.out.println("I got here Prempt");
        });
    }

    @Test
    void testAssumptionTrue() {
        assumeTrue("GURU".equalsIgnoreCase(System.getenv("GURU_RUNTIME")));
    }

    @Test
    void testAssumptionIsTrue() {
        assumeTrue("GURU".equalsIgnoreCase("GURU"));
    }

    @EnabledOnOs(OS.MAC)
    @Test
    void testMeOnMacOS() {

    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    void testMeOnWindows() {

    }

    @EnabledOnJre(JRE.JAVA_8)
    @Test
    void testMeOnJava8() {

    }

    @EnabledOnJre(JRE.JAVA_11)
    @Test
    void testMeOnJava11() {

    }

    @EnabledOnJre(JRE.OTHER)
    @Test
    void testMeOnJavaOther() {

    }

    @EnabledIfEnvironmentVariable(named = "USER", matches = "bvega")
    @Test
    void testIfUserBvega() {
    }

    @EnabledIfEnvironmentVariable(named = "USER", matches = "noc")
    @Test
    void testIfUserNoc() {

    }
}