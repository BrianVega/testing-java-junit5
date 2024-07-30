package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;

// To set beforeAll as not static
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// Tag to identify our tests, this is class level, it also helps us to create configurations upon certain tagged classes
@Tag("controller")
public interface ControllerTests {

    @BeforeAll
    default void beforeAll() {
        System.out.println("Before All. Let's do something here");
    }
}
