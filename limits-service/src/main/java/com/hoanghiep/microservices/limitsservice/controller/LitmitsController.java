package com.hoanghiep.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoanghiep.microservices.limitsservice.bean.Limits;
import com.hoanghiep.microservices.limitsservice.configuration.Configuration;

@RestController
public class LitmitsController {

	@Autowired
	private Configuration configuration;
	@GetMapping("/limits")
	public Limits retrieveLimits() {
		return new Limits(configuration.getMinimum(), configuration.getMaximum());
	}
}
