package com.mizore.gwt.logging.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.mizore.gwt.logging.client.console.view.LoggerConsoleDisplay;
import com.mizore.gwt.logging.client.console.view.LoggingConsolePopup;
import com.mizore.gwt.logging.client.control.presenter.LoggingPresenter.LoggingDisplay;
import com.mizore.gwt.logging.client.control.view.LoggerUiImpl;
import com.mizore.gwt.logging.client.report.view.ReporterDisplay;
import com.mizore.gwt.logging.client.report.view.ReporterDisplayImpl;
import com.mizore.gwt.logging.client.standard.starter.interfaces.StarterDisplay;
import com.mizore.gwt.logging.client.standard.starter.view.StarterViewImpl;

public class LoggingModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(LoggingDisplay.class).to(LoggerUiImpl.class);
		bind(LoggerConsoleDisplay.class).to(LoggingConsolePopup.class);
		bind(ReporterDisplay.class).to(ReporterDisplayImpl.class);
		bind(StarterDisplay.class).to(StarterViewImpl.class);
	}

}
