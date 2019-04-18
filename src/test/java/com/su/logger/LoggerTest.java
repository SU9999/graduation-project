package com.su.logger;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Data
public class LoggerTest {
    private Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void testLogger(){
        logger.debug("debug....");
        logger.error("error....");
        logger.info("info....");
    }
}
