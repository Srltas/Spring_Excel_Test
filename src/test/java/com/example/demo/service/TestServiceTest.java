package com.example.demo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestServiceTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired private TestService testService;

    /**
     * ((num / (1000 * 60)) % 60)
     */
    @Test
    @DisplayName("분으로 계산")
    void test1() {
        double result = testService.test(28800000);

        log.info("result={}",result);

        assertThat(result).isEqualTo(480);
    }

    /**
     * ((num / (1000 * 60 * 60)) % 24)
     */
    @Test
    @DisplayName("시간으로 계산")
    void test2() {
        assertThat(testService.test2(28800000)).isEqualTo(8);
    }
}