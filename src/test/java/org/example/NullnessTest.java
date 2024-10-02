package org.example;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class NullnessTest {
    @Test
    public void nullCheckOnString() {
        String str = null;
        // logger.info(String.format("%d", str.length()));
    }
}
