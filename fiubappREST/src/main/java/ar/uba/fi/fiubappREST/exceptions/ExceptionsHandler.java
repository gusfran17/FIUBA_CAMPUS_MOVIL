package ar.uba.fi.fiubappREST.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.domain.ErrorResponse;
import ar.uba.fi.fiubappREST.services.MessageService;

@ControllerAdvice
public class ExceptionsHandler {
	
	private static final String UNEXPECTED_ERROR_CODE = "0000";
	
	private MessageService messageService;
	
	@Autowired	
	public ExceptionsHandler(MessageService messageService) {
		super();
		this.messageService = messageService;
	}

	private ErrorResponse buildResponse(BusinessException exception) {
		String message = messageService.getMessage(exception.getCode(), exception.getParams());
		return new ErrorResponse(exception.getCode(), message);
	}
	
	@ExceptionHandler(DateFormatException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public @ResponseBody ErrorResponse handle(DateFormatException exception){
		return buildResponse(exception);
	}
	
	@ExceptionHandler(CareerNotFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public @ResponseBody ErrorResponse handle(CareerNotFoundException exception){
		return buildResponse(exception);
	}
	
	@ExceptionHandler(CareerAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public @ResponseBody ErrorResponse handle(CareerAlreadyExistsException exception){
		return buildResponse(exception);
	}
	
	@ExceptionHandler(OrientationAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public @ResponseBody ErrorResponse handle(OrientationAlreadyExistsException exception){
		return buildResponse(exception);
	}
	
	@ExceptionHandler(StudentAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public @ResponseBody ErrorResponse handle(StudentAlreadyExistsException exception){
		return buildResponse(exception);
	}
	
	@ExceptionHandler(StudentNotFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public @ResponseBody ErrorResponse handle(StudentNotFoundException exception){
		return buildResponse(exception);
	}
	
	@ExceptionHandler(CareerAlreadyExistsForStudent.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public @ResponseBody ErrorResponse handle(CareerAlreadyExistsForStudent exception){
		return buildResponse(exception);
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public @ResponseBody ErrorResponse handle(InvalidCredentialsException exception){
		return buildResponse(exception);
	}
	
	@ExceptionHandler(StudentSessionNotFoundException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN) 
	public @ResponseBody ErrorResponse handle(StudentSessionNotFoundException exception){
		return buildResponse(exception);
	}
	
	@ExceptionHandler(OperationNotAllowedFotStudentSessionException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN) 
	public @ResponseBody ErrorResponse handle(OperationNotAllowedFotStudentSessionException exception){
		return buildResponse(exception);
	}
	
	
	
	
	@ExceptionHandler(InvalidUserException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public @ResponseBody ErrorResponse handle(InvalidUserException exception){
		return new ErrorResponse(exception.getCode(), exception.getMessage());
	}
		
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) 
	public @ResponseBody ErrorResponse handle(RuntimeException exception){
		String message = messageService.getMessage(UNEXPECTED_ERROR_CODE);
		return new ErrorResponse(UNEXPECTED_ERROR_CODE, message);
	}
	
}
