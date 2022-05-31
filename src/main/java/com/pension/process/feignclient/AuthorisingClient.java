package com.pension.process.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "Authorizatiion-Microservice", url = "http://localhost:8000/auth/api")
public interface AuthorisingClient {

	@PostMapping("/authorize")
	public boolean authorizeTheRequest(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader);
}
