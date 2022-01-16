package com.challenge.crawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AutoViewController {
	
	@GetMapping("/")
	public String welcomePage(Model model)
	{
		return "welcome";
	}
	
	@GetMapping("/show")
	public String showAutomobileList(Model model)
	{
		return "automobile-info";
	}
	
	/*@GetMapping("/welcome")
	public String welcomePage(Model model)
	{
		return "welcome";
	}*/

}
