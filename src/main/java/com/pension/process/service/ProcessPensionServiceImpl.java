package com.pension.process.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pension.process.exception.AadharNumberNotFound;
import com.pension.process.exception.AuthorizationException;
import com.pension.process.exception.PensionerDetailException;
import com.pension.process.feignclient.PensionDisbursementFeignClient;
import com.pension.process.feignclient.PensionerDetailFeignClient;
import com.pension.process.model.PensionDetail;
import com.pension.process.model.PensionerDetail;
import com.pension.process.model.PensionerInput;
import com.pension.process.model.ProcessPensionInput;
import com.pension.process.model.ProcessPensionResponse;

@Service
public class ProcessPensionServiceImpl implements ProcessPensionService {

	@Autowired
	private PensionerDetailFeignClient pensionerDeatailFeignClient;
	
	@Autowired
	private PensionDisbursementFeignClient pensionDisbursementFeignClient;
	
	@Override
	public PensionDetail CalculatePension(String token,PensionerInput pensionerInput) throws PensionerDetailException, AuthorizationException, AadharNumberNotFound
	
	{
		
	
		
		PensionerDetail pensionerDetail = null;
		
		try
		{
			pensionerDetail = pensionerDeatailFeignClient.getPensionerDetailByAadhaar(token, pensionerInput.getAadharNumber());
			
		}
		catch (AadharNumberNotFound e) {
			throw new AadharNumberNotFound("Aadhar Card Number is not Valid. Please check it and try again");
		}
		
		
		if(pensionerInput.getAadharNumber() == pensionerDetail.getAadharNumber() && pensionerInput.getName().equalsIgnoreCase(pensionerDetail.getName()) && pensionerInput.getPan().equalsIgnoreCase(pensionerDetail.getPan()))
		{
			
			double salary = pensionerDetail.getSalaryEarned();
			double allowances = pensionerDetail.getAllowances();
			double pensionAmount = 0;
			if(pensionerInput.getPensionType().equalsIgnoreCase("self"))
			{
				pensionAmount = 0.8*salary + allowances;
			}
			else if(pensionerInput.getPensionType().equalsIgnoreCase("family"))
			{
				pensionAmount = 0.5 * salary + allowances;
			}
			
			PensionDetail pensionDetail = new PensionDetail();
			pensionDetail.setName(pensionerDetail.getName());
			pensionDetail.setDateOfBirth(pensionerDetail.getDateOfBirth());
			pensionDetail.setPan(pensionerDetail.getPan());
			pensionDetail.setPensionAmount(pensionAmount);
			pensionDetail.setPensionType(pensionerDetail.getPensionType());
			
			return pensionDetail;
		}
		else
		{
			throw new PensionerDetailException("Invalid pensioner detail provided, please provide valid detail.");
		}
		
	}
	
	@Override
	public ProcessPensionResponse getCode(String token,ProcessPensionInput processPensionInput) throws AuthorizationException, AadharNumberNotFound
	{	try
		{
		return pensionDisbursementFeignClient.getResponse(token, processPensionInput);
		}
	
		catch(AadharNumberNotFound e)
		{
			throw new AadharNumberNotFound("Aadhar Card Number is not Valid. Please check it and try again");
		}
		 
	
		 
	}
	
	
}
