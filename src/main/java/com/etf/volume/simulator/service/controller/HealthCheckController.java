package com.etf.volume.simulator.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Shawn Stewart
 */
@RestController
public class HealthCheckController {
	
	@RequestMapping(value="/healthCheck", method=RequestMethod.GET)
	public String healthCheck() {
		return "Up and Running...";
	}
}
