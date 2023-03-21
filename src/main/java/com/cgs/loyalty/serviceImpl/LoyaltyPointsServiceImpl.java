package com.cgs.loyalty.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cgs.loyalty.dto.PointsValueDto;
import com.cgs.loyalty.entity.customer.LoyaltyPointsValues;
import com.cgs.loyalty.repository.LoyaltyPointsRepository;
import com.cgs.loyalty.service.LoyaltyPointsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoyaltyPointsServiceImpl implements LoyaltyPointsService {

	@Autowired
	private LoyaltyPointsRepository loyaltyPointsRepository;

	@Autowired
	private ModelMapper modelMapper;

	// setting values for calculating points
	@Override
	public PointsValueDto savePointsData(PointsValueDto pointsValueDto) {
		log.info("In set points values service");

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
