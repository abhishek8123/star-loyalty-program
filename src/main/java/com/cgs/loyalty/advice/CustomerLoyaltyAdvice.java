package com.cgs.loyalty.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.cgs.loyalty.exception.BadRequestException;
import com.cgs.loyalty.exception.CustomerNotExistException;
import com.cgs.loyalty.exception.IdAlreadyExistException;
import com.cgs.loyalty.exception.ListIsEmptyException;
import com.cgs.loyalty.exception.PointsNotAvailableException;

@ControllerAdvice
public class CustomerLoyaltyAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<APIResponce> handledBadRequestException(BadRequestException e) {
		APIResponce apiResponce = new APIResponce();
		apiResponce.setStatus(HttpStatus.BAD_REQUEST.value());
		apiResponce.setErrors(e.getErrors());
		return ResponseEntity.status(400).body(apiResponce);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		return super.handleMissingPathVariable(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handleNullPointerException(NullPointerException nullPointerException) {

		return new ResponseEntity<String>("Input is Null or Incurrect ", HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(CustomerNotExistException.class)
	public ResponseEntity<String> handleCustomerNotExistException(CustomerNotExistException customerNotExistException) {

		return new ResponseEntity<String>("Given id not exist in database", HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(ListIsEmptyException.class)
	public ResponseEntity<String> handleListIsEmptyException(ListIsEmptyException listIsEmptyException) {

		return new ResponseEntity<String>("data base is empty there is no any data present", HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception exception) {

		return new ResponseEntity<String>("Something went wrong plese try again", HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(IdAlreadyExistException.class)
	public ResponseEntity<String> handleException(IdAlreadyExistException idAlreadyExistException) {

		return new ResponseEntity<String>("id already exist in data base", HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(PointsNotAvailableException.class)
	public ResponseEntity<String> handleException(PointsNotAvailableException pointsNotAvailableException) {

		return new ResponseEntity<String>("your account don't have this much points please check existing points !!", HttpStatus.BAD_REQUEST);

	}

}
