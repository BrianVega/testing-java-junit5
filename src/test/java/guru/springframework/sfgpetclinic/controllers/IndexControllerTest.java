package guru.springframework.sfgpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}