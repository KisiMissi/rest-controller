package org.kaoden.ws.homework;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        properties = {"DB_LOGIN=vladislav", "DB_PASSWORD=admin"}
)
public class AppTest {

    @Test
    void emptyTest() {
    }
}