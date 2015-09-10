/*
 * Gwt Logger Library 
 * 
 * 
 *
 * 
 * 
 * 
 */
package com.mizore.gwt.logging.client;

import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;
import com.mizore.gwt.logging.client.report.ReportI;

/**
 * @author svandecappelle
 * @since 14 oct. 2013
 * @version 1.0.0
 * 
 * 
 */
public final class LoggingConfigurationsImpl {

	private static final ReportI reportConfiguration = GWT.create(ReportI.class);

	private static final Logger rootLogger = Logger.getLogger("");

	/**
	 * Default constructor
	 * 
	 */
	private LoggingConfigurationsImpl() {
	}

	/**
     * 
     */
	public static boolean isReportActivated() {
		return reportConfiguration.isActive();
	}

	public static Logger getRootLogger() {
		return rootLogger;
	}
}
