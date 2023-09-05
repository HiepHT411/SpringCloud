package com.hoanghiep.resfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersionVersioningController {
	//2 cach dau tien end user friendly hon
	//2 cach cuoi se rat phuc tap khi caching
	
	// URI versioning			// cach nay pollute the URI
	@GetMapping("/v1/person")
	public PersonV1 getPersonV1() {
		return new PersonV1("HiepHT");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 getPersonV2() {
		return new PersonV2( new Name("Hiep", "Hoang Tuan"));
	}
	
	//========================================================================================
	// Request Parameter Versioning
	//localhost:8080/person/param?version=1
	@GetMapping(value = "/person/param", params = "version=1")
	public PersonV1 getPersonWithParamsV1() {
		return new PersonV1("HiepHT");
	}
	
	//localhost:8080/person/param?version=2
	@GetMapping(value = "/person/param", params = "version=2")
	public PersonV2 getPersonWithParamsV2() {
		return new PersonV2( new Name("Hiep", "Hoang Tuan"));
	}
	
	
	//========================================================================================
	//Headers verioning
	
	// localhost:8080/person/header 
	// headers key: X-API-VERSION  value: 1
	@GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
	public PersonV1 getPersonWithHeadersV1() {
		return new PersonV1("HiepHT");
	}
	
	// localhost:8080/person/header 
	// headers key: X-API-VERSION  value: 2
	@GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
	public PersonV2 getPersonWithHeadersV2() {
		return new PersonV2( new Name("Hiep", "Hoang Tuan"));
	}
	
	
	//=========================================================================================
	//Accept headers versioning or Mime type version
	// localhost:8080/person/produces
	// headers key: Accept  value: application/vnd.company.app-v1+json
	@GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v1+json")
	public PersonV1 getPersonWithProducesV1() {
		return new PersonV1("HiepHT");
	}
	
	// localhost:8080/person/produces
	// headers key: Accept  value: application/vnd.company.app-v2+json
	@GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v2+json")
	public PersonV2 getPersonWithProducesV2() {
		return new PersonV2( new Name("Hiep", "Hoang Tuan"));
	}
	
	
}
