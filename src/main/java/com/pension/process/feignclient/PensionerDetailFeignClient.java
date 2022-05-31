package com.pension.process.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.pension.process.exception.AadharNumberNotFound;
import com.pension.process.exception.AuthorizationException;
import com.pension.process.model.PensionerDetail;

import io.swagger.annotations.ApiParam;

@FeignClient(name ="PensionerDetail-Microservice",url = "http://localhost:6001/pensioner/api")
public interface PensionerDetailFeignClient {
	
	@GetMapping("/PensionerDetailByAadhaar/{aadharNumber}")
	public PensionerDetail getPensionerDetailByAadhaar(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@ApiParam(name = "aadharNumber", value = "Aadhar Card Number") 
			@PathVariable long aadharNumber) throws AuthorizationException, AadharNumberNotFound;
	

}
