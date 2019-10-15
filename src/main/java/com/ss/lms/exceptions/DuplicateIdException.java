package com.ss.lms.exceptions;

/*
 * Thrown if there is an attempt to insert a new entity
 * 	whose id matches that of an already stored entity
 */

public class DuplicateIdException extends Exception {
	
	public static final long serialVersionUID = 1L;
}
