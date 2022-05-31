package com.pension.process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pension.process.exception.AadharNumberNotFound;
import com.pension.process.exception.AuthorizationException;
import com.pension.process.exception.PensionerDetailException;
import com.pension.process.feignclient.AuthorisingClient;
import com.pension.process.model.PensionDetail;
import com.pension.process.model.PensionerInput;
import com.pension.process.model.ProcessPensionInput;
import com.pension.process.model.ProcessPensionResponse;
import com.pension.process.service.ProcessPensionServiceImpl;

import io.swagger.annotations.ApiOperation;
@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class ProcessPensionController {

	@Autowired
	ProcessPensionServiceImpl processPensionServiceImpl;
	
	@Autowired
	private AuthorisingClient authorisingClient;
	
	@PostMapping("/PensionDetail")
	@ApiOperation(notes = "Returns the Pension Details", value = "Find the pension details")
	public PensionDetail getPensionDetail(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody PensionerInput pensionerInput) throws AuthorizationException, PensionerDetailException, AadharNumberNotFound
	{
	
			if(authorisingClient.authorizeTheRequest(requestTokenHeader)) 
			{
				
				return processPensionServiceImpl.CalculatePension(requestTokenHeader,pensionerInput);
			}
			else
			{
				throw new AuthorizationException("Not allowed AuthorizationException");
			}
	}

	
	@PostMapping("/ProcessPension")
	@ApiOperation(notes = "Returns the Process Responce Code(10 or 21)", value = "Find Process Responce Code, If Process code is 10 then Suceess and 21 means not success")
	public ProcessPensionResponse getprocessingCode(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody ProcessPensionInput processPensionInput) throws AuthorizationException, AadharNumberNotFound
	{
		if(authorisingClient.authorizeTheRequest(requestTokenHeader)) 
		{
			return processPensionServiceImpl.getCode(requestTokenHeader,processPensionInput);
		}
		else
		{
			throw new AuthorizationException("Not allowed AuthorizationException");
		}
		
		
	}
	

	
}
