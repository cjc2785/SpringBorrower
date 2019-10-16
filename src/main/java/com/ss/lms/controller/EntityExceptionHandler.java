package com.ss.lms.controller;

import com.ss.lms.exceptions.*;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class  EntityExceptionHandler 
{
	@ExceptionHandler(EntityDoesNotExistException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody String handleResourceNotFound() { 
		return "Resource not found";
	}
}