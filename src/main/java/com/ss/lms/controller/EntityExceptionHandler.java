package com.ss.lms.controller;

import com.ss.lms.exceptions.*;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class  EntityExceptionHandler 
{
	@ExceptionHandler(EntityDoesNotExistException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFound() { 
		return "Resource not found";
	}
}