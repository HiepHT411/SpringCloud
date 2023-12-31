package com.hoanghiep.microservices.apigateway.config;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
	public RouteLocator gatewayLocator (RouteLocatorBuilder builder) {
		
		Function<PredicateSpec, Buildable<Route>> routeFunction 
						= p -> p.path("/get") 						//if the request come to /get
								.filters(f -> f.addRequestHeader("MyHeader", "MyURI")
												.addRequestParameter("Param", "Value"))
									.uri("http://httpbin.org:80");	//then we redirect it to this specific uri
		//customized route
		return builder.routes()
				.route(routeFunction)
				.route(p -> p.path("/currency-exchange/**")
								.uri("lb://currency-exchange"))
				.route(p -> p.path("/currency-conversion/**")
								.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-feign/**")
								.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-new/**")			// try redirect new to feign
								.filters(f -> f.rewritePath(
										"/currency-conversion-new/(?<segment>.*)",
										"/currency-conversion-feign/${segment}"))
								.uri("lb://currency-conversion"))
				.build();	
	}
}
