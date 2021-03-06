package com.challenge.crawler.test;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.challenge.crawler.dto.AutomobileInfo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestMainController {
	
	 @LocalServerPort
	 private int port;

	 @Autowired
	 private TestRestTemplate restTemplate;

	 @Test
	 public void getStatus() throws Exception {

	        ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/api/fetch/names").toString(), String.class);	        
	        
	        assertEquals(200, response.getStatusCodeValue());

	    }

}
