package com.cgs.loyalty.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgs.loyalty.dto.PointsValueDto;
import com.cgs.loyalty.service.LoyaltyPointsService;

@RestController
@RequestMapping(value = "/points")
public class CustomerPointsController {

	@Autowired
	private LoyaltyPointsService loyaltyPointsService;

	// setting the values for calculating the points
	@PostMapping("/setPointsValues")
	public ResponseEntity<PointsValueDto> setPointsDetails(@RequestBody PointsValueDto pointsValueDto) {

		PointsValueDto pointsValueDto1 = loyaltyPointsService.savePointsData(pointsValueDto);

		return new ResponseEntity<PointsValueDto>(pointsValueDto1, HttpStatus.OK);
	}

}
