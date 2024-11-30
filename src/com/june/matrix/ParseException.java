/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.calc Absolute.java
 * DESC : Natural language processing computational engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.matrix;

public class ParseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	final Token token;

	/**
	 * Constructors
	 * @param message
	 */
	public ParseException(String message) {
		super(message);
		this.token = null;
	}
	
	/**
	 * Constructors
	 * @param message
	 */
	public ParseException(String message, Token token) {
		super(message);
		this.token = token;
	}
}
