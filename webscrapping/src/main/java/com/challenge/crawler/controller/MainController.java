package com.challenge.crawler.controller;

import java.util.List;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.crawler.dto.AutomobileInfo;
import com.challenge.crawler.service.MyWebCrawler;
import com.fasterxml.jackson.databind.JsonNode;


@RestController
@RequestMapping("/api")
public class MainController {	
	
	@Autowired
	MyWebCrawler myCrawler;	
	
	/**
	 * This API will fetch all the names of the automobiles from the automobile column of the tables of Crawled website
	 * @author Bijaya
	 * @return
	 */
	@GetMapping(value = "/fetch/names",produces = MediaType.APPLICATION_JSON_VALUE)
	public SortedSet<String> fetchAutomobileNames()
	{
		return this.myCrawler.getAutomobileNames();		
	}
	
	/**
	 * This API will return the details based on the name passed in the json request 
	 * @param body
	 * @return
	 */
	@PostMapping(value = "/details",produces = MediaType.APPLICATION_JSON_VALUE)
	public AutomobileInfo fetchAutomobileNames(@RequestBody JsonNode body)
	{
		String name = "";
		if(body != null && body.get("carName") != null)
		{
			name = body.get("carName").asText();
		}
		return this.myCrawler.getAutomobileDetailsByName(name);
	}
	
	/**
	 * This API will return all the details of all the automobiles in the automobile column of the crawled website
	 * Here for this task only distinct names are taken .However we can definitely take all the duplicates also with little modification to code
	 * @return
	 */
	@GetMapping(value = "/fetch/all/details",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AutomobileInfo> fetchAllAutomobileDetails()
	{
		return this.myCrawler.getAutomobileDetails();
		
	}

}
