package com.cgs.loyalty.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgs.loyalty.advice.ErrorDetails;
import com.cgs.loyalty.dto.PointsValueDto;
import com.cgs.loyalty.entity.customer.LoyaltyPointsValues;
import com.cgs.loyalty.exception.BadRequestException;
import com.cgs.loyalty.repository.LoyaltyPointsRepository;
import com.cgs.loyalty.service.LoyaltyPointsService;
import com.cgs.loyalty.util.PointsValueValidation;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoyaltyPointsServiceImpl implements LoyaltyPointsService {

	@Autowired
	private LoyaltyPointsRepository loyaltyPointsRepository;
	
	@Autowired
	private PointsValueValidation pointsValidation;

	@Autowired
	private ModelMapper modelMapper;

	// setting values for calculating points
	@Override
	public PointsValueDto savePointsData(PointsValueDto pointsValueDto) {
		log.info("In set points values service");

		List<ErrorDetails> errors = pointsValidation.validatepointsvalues(pointsValueDto);
		if (errors.size() > 0) {
			throw new BadRequestException(errors);
		}
		
		LoyaltyPointsValues pointsValues = dtoToLoyCustomerPoints(pointsValueDto);
		loyaltyPointsRepository.save(pointsValues);
		return pointsValueDto;

	}
	 
	
	
	

	// ------------------------------------------------------------------------------------//

	// DTO to LoyaltyPointsValue
	public LoyaltyPointsValues dtoToLoyCustomerPoints(PointsValueDto pointsValueDto) {

		LoyaltyPointsValues pointsDetails = this.modelMapper.map(pointsValueDto, LoyaltyPointsValues.class);
		return pointsDetails;
	}

	// LoyaltyLoyaltyPointValue to DTO
	public PointsValueDto loyCustomerPointsToDto(LoyaltyPointsValues loyaltyPointsValues) {

		PointsValueDto pointstDto = this.modelMapper.map(loyaltyPointsValues, PointsValueDto.class);
		return pointstDto;
	}

	// ------------------------------------------------------------------------------------//

}
