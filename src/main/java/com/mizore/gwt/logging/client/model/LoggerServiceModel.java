/*
 * Gwt Logger Library 
 * 
 * 
 *
 * 
 * 
 * 
 */
package com.mizore.gwt.logging.client.model;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mizore.gwt.logging.client.service.LoggerServiceAsync;

/**
 * @author svandecappelle
 * @since 14 oct. 2013
 * @version 1.0.0
 * 
 * 
 */
@Singleton
public final class LoggerServiceModel {

	@Inject
	private LoggerServiceAsync service;

	/**
	 * Default constructor
	 * 
	 */
	public LoggerServiceModel() {
	}

	/**
	 * @param throwable
	 * 
	 */
	public void report(String message) {
		this.service.report(message, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				// Nothing to do
			}

			@Override
			public void onFailure(Throwable caught) {
				// Nothing to do
			}
		});
	}
}
