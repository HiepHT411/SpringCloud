package com.hoanghiep.microservices.currencyexchangeservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CircuitBreakerController {

	
	@GetMapping("/sample-api")
	//@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")		//"default": retry 3 times
	@CircuitBreaker(name="default", fallbackMethod = "hardcodedResponse")
	@RateLimiter(name = "default")	//10s -> 10000 calls to the sample api (configure in properties)
	public String sampleApi() {
		log.info("Sample API call receive");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-api", String.class);
		
		return forEntity.getBody();
	}
	
	@GetMapping("/sample-api-2")
	@RateLimiter(name = "default")	//10s -> 10000 calls to the sample api (configure in properties)
	public String sampleApi2() {
		log.info("Sample API call receive");
		
		return "sample api 2";
	}
	
	//Bulkhead to configure number of concurrent call
	@GetMapping("/sample-api-3")
	@Bulkhead(name = "sample-api-3")
	public String sampleApi3() {
		log.info("Sample API call receive");
		
		return "sample api 3";
	}
	
	public String hardcodedResponse(Exception ex) {
		return "fallback-response";
	}
}
