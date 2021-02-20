package com.beerhouse.exceptions;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.beerhouse.controllers.BeerController;

@ControllerAdvice(assignableTypes = BeerController.class)
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = {BeerNotFoundException.class})
	public ResponseEntity<Object> beerNotFoundException(BeerNotFoundException e) {
		    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {ParseJsonPatchException.class})
	public ResponseEntity<Object> parseJsonPatchException(ParseJsonPatchException e) {
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, 
        HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
