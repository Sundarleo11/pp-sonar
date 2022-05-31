package com.pension.process.service;

import com.pension.process.exception.AadharNumberNotFound;
import com.pension.process.exception.AuthorizationException;
import com.pension.process.exception.PensionerDetailException;
import com.pension.process.model.PensionDetail;
import com.pension.process.model.PensionerInput;
import com.pension.process.model.ProcessPensionInput;
import com.pension.process.model.ProcessPensionResponse;

public interface ProcessPensionService {
	
	public PensionDetail CalculatePension(String token,PensionerInput pensionerInput) throws PensionerDetailException, AuthorizationException, AadharNumberNotFound;
	public ProcessPensionResponse getCode(String token,ProcessPensionInput processPensionInput) throws AuthorizationException, AadharNumberNotFound;
}
