package com.mizore.gwt.logging.client.service;

import java.util.logging.LogRecord;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("log_service")
public interface LoggerService extends RemoteService {
	/**
	 * Log on server.
	 * 
	 * @param record
	 *            a record to display in server side.
	 */
	void log(LogRecord record);

	/**
	 * Log a report on service side through the logger.
	 * 
	 * May do other things... Override service to implements you own behavior.
	 * 
	 * @param message
	 *            error to report formatted in String format.
	 */
	void report(String message);
}
