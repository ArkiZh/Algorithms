package com.arki.algorithms.common;

import org.junit.Test;

public class LoggerTest {
    @Test
    public void testInfo(){
        Logger.info("Hello log.info(msg)");
        Logger.info("Hello log.info(msg, arguments)  [{}]", "success");
    }
}
