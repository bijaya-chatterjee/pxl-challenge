package com.challenge.crawler.test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.challenge.crawler.service.MyWebCrawler;

@SpringBootTest
public class TestMyWebCrawler {
	
	@Autowired
	MyWebCrawler webCrawler;
	
    @Test
    void testGetAutomobileDetails() {
        assertEquals(517, webCrawler.getAutomobileDetails().size());
    }
	
    @Test
    void testGetAutomobileNames() {
        assertEquals(517, webCrawler.getAutomobileNames().size());
    }


}
