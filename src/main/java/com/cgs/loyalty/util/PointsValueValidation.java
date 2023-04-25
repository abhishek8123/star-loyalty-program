package com.cgs.loyalty.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cgs.loyalty.advice.ErrorDetails;
import com.cgs.loyalty.dto.PointsValueDto;
import com.cgs.loyalty.entity.customer.LoyaltyPointsValues;
import com.cgs.loyalty.exception.IdAlreadyExistException;
import com.cgs.loyalty.repository.LoyaltyPointsRepository;

@Component
public class PointsValueValidation {

	@Autowired
	private LoyaltyPointsRepository pointsRepository;

	// validate the points value request
	public List<ErrorDetails> validatepointsvalues(PointsValueDto valueDto) {

		Optional<LoyaltyPointsValues> account = pointsRepository.findById(valueDto.getId());

		if (account.isEmpty()) {

			List<ErrorDetails> errors = new ArrayList<>();

			// Id
			if (valueDto.getId().isEmpty() || valueDto.getId().length() == 0) {
				ErrorDetails error = new ErrorDetails("valueId", "ID should not be empty !!");
				errors.add(error);
			}

			// Name
			if (valueDto.getName().isEmpty() || valueDto.getName().length() <= 3) {
				ErrorDetails error = new ErrorDetails("Name", "Adder Name shold be Have At least 3 charecters !!");
				errors.add(error);
			}

			// Bank Name
			if (valueDto.getBank_name().isEmpty() || valueDto.getBank_name().length() <= 3) {
				ErrorDetails error = new ErrorDetails("Bank Name", "Bank Name Should be at least 3 Charecters !!");
				errors.add(error);
			}

			// Transaction points
			if (valueDto.getPoints_per_trans().isEmpty() || valueDto.getPoints_per_trans() == null) {
				ErrorDetails error = new ErrorDetails("Points Per Transaction", "transaction points Should not be null or empty !!");
				errors.add(error);
			}

			// Per Points
			if (valueDto.getPer_points().isEmpty() || valueDto.getPer_points() == null) {
				ErrorDetails error = new ErrorDetails("Per Points", "Per Points Should not be null or empty !!");
				errors.add(error);
			}

			// ammount
			if (valueDto.getAmmount().isEmpty() || valueDto.getAmmount() == null) {
				ErrorDetails error = new ErrorDetails("amount", "Amount Should not be null or empty !!");
				errors.add(error);
			}

			return errors;

		} else {
			throw new IdAlreadyExistException();
		}
	}
}
