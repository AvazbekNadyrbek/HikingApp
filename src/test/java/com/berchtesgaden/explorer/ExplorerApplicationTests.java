package com.berchtesgaden.explorer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ExplorerApplicationTests {

	@Test
	void contextLoads() {
	}

    @Test
    void concatTest() {

        String stringOne = "Hello ";
        String stringTwo = "World";
        String stringThree = "test";

        assertEquals("Hello World", stringOne + stringTwo);

    }
}
