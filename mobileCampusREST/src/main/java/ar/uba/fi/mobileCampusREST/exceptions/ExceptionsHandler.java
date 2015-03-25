package ar.uba.fi.mobileCampusREST.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.mobileCampusREST.domain.ErrorResponse;
import ar.uba.fi.mobileCampusREST.services.MessageService;

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
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public @ResponseBody ErrorResponse handle(UserNotFoundException exception){
		return buildResponse(exception);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public @ResponseBody ErrorResponse handle(UserAlreadyExistsException exception){
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
