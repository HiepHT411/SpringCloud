package com.hoanghiep.resfulwebservices;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path = "/hello-world")
	public String hello() {
		return "hello world";
	}
	
//	@GetMapping(path = "/hello-bean")
//	public HelloBean helloBean() {
//		return new HelloBean("hello bean");
//	}
	
	@GetMapping(path = "/hello-name/{name}")
	public String helloName(@PathVariable String name) {
		return String.format("Hello %s", name );
	}
	
	@GetMapping(path = "/hello-world-internationalized")
	public String helloWorldInternationalized(
						//@RequestHeader(name = "Accept-Language", required = false) Locale locale
						) {
		//en Hello world
		//nl Goede Morgen
		//fr Bonjour
		
		return messageSource.getMessage("good.morning.message", null, "Default Message",
																		//locale);
																LocaleContextHolder.getLocale());
	}
}
