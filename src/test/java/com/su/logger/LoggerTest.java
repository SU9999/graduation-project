package com.su.logger;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Data
@Slf4j
public class LoggerTest {
//    private Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void testLogger(){
//        logger.debug("debug....");
//        logger.error("error....");
//        logger.info("info....");

        log.debug("debug...");
        log.error("error....");
        log.info("info....");

        String name = "sutong";
        String pwd = "123456";
        log.info("name: {}, password: {}\n", name, pwd);
    }
}
