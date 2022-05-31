package com.pension.process.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.pension.process.exception.AadharNumberNotFound;
import com.pension.process.exception.AuthorizationException;
import com.pension.process.model.ProcessPensionInput;
import com.pension.process.model.ProcessPensionResponse;


@FeignClient(name = "PensionDisbursement-Microservices",url = "http://localhost:7000/disbursement/api")
public interface PensionDisbursementFeignClient {
	
	@PostMapping("/disbursePension")
	public ProcessPensionResponse getResponse(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody ProcessPensionInput processpensionInput) throws AuthorizationException, AadharNumberNotFound;
	
}
