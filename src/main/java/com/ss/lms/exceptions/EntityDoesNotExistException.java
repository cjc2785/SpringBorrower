package com.ss.lms.exceptions;

/*
 * Thrown if there is an attempt to insert or update an 
 *   entity who has a field referencing another entity that does
 *   not exist
 */

public class EntityDoesNotExistException extends Exception {

	
	public static final long serialVersionUID = 1L;

}
