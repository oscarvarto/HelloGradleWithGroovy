package org.example;

import lombok.extern.java.Log;
import org.testng.annotations.Test;

@Log
public class NullnessChecks {
    @Test
    public void nullCheckOnString() {
        String str = null;
        log.info(String.format("%d", str.length()));
    }
}
