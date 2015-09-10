package com.mizore.gwt.logging.client.service;

import java.util.logging.LogRecord;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>MyService</code>.
 */
public interface LoggerServiceAsync {
	void log(LogRecord record, AsyncCallback<Void> callback);

	/**
	 * @param message
	 */
	void report(String message, AsyncCallback<Void> callback);

}
