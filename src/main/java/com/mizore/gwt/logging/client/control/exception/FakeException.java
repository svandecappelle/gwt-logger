/*
 * Gwt Logger Library 
 * 
 * 
 *
 * 
 * 
 * 
 */
package com.mizore.gwt.logging.client.control.exception;

/**
 * @author svandecappelle
 * @since 14 oct. 2013
 * @version 1.0.0
 * 
 * 
 */
public class FakeException extends RuntimeException {

	/**
     * 
     */
	private static final long serialVersionUID = 3823363432049913282L;

	/**
	 * Default constructor
	 * 
	 */
	public FakeException() {
		super("Fake exception");
	}
}
