/*
 * Gwt Logger Library 
 * 
 * 
 *
 * 
 * 
 * 
 */
package com.mizore.gwt.logging.client.report.presenter;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mizore.gwt.logging.client.LoggingConfigurationsImpl;
import com.mizore.gwt.logging.client.model.LoggerServiceModel;
import com.mizore.gwt.logging.client.report.view.ReporterDisplay;

/**
 * @author svandecappelle
 * @since 9 oct. 2013
 * @version 1.0.0
 * 
 * 
 */
@Singleton
public class BugReporterPresenter extends WidgetPresenter<ReporterDisplay> {

	private Throwable throwable;

	@Inject
	private LoggerServiceModel model;

	private Boolean isActive = LoggingConfigurationsImpl.isReportActivated();

	/**
	 * Default constructor
	 * 
	 * @param display
	 * @param eventBus
	 */
	@Inject
	public BugReporterPresenter(ReporterDisplay display, EventBus eventBus) {
		super(display, eventBus);
	}

	@Override
	protected void onBind() {
		registerHandler(display.getReportClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				model.report(getMessage(throwable));
				hide();
			}
		}));
		registerHandler(display.getDetailCloseHandler().addCloseHandler(new CloseHandler<DisclosurePanel>() {

			@Override
			public void onClose(CloseEvent<DisclosurePanel> event) {
				display.hideDetail();
			}
		}));
		registerHandler(display.getDetailOpenHandler().addOpenHandler(new OpenHandler<DisclosurePanel>() {

			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				display.showDetail();
			}
		}));
		registerHandler(display.getCancelClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		}));

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				Logger.getLogger("").log(Level.SEVERE, "An error have been catch by logging console: ", e);
				if (LoggingConfigurationsImpl.isReportActivated() && isActive) {
					BugReporterPresenter.this.createReport(e);
					BugReporterPresenter.this.revealDisplay();
				}
			}
		});
	}

	/**
     * 
     */
	protected void hide() {
		display.hide();
		RootLayoutPanel.get().remove(display.asWidget());
		throwable = null;
	}

	@Override
	protected void onUnbind() {

	}

	@Override
	protected void onRevealDisplay() {
		if (LoggingConfigurationsImpl.isReportActivated()) {
			if (!display.asWidget().isAttached()) {
				RootLayoutPanel.get().add(display.asWidget());
			}
		}
		if (throwable != null) {
			display.setContent(getMessage(throwable));
			display.show();
		}

	}

	private static String getMessage(Throwable throwable) {
		String ret = "";
		while (throwable != null) {
			if (throwable instanceof com.google.gwt.event.shared.UmbrellaException) {
				for (Throwable thr2 : ((com.google.gwt.event.shared.UmbrellaException) throwable).getCauses()) {
					if (ret != "")
						ret += "\nCaused by: ";
					ret += thr2.toString();
					ret += "\n  at " + getMessage(thr2);
				}
			} else if (throwable instanceof com.google.web.bindery.event.shared.UmbrellaException) {
				for (Throwable thr2 : ((com.google.web.bindery.event.shared.UmbrellaException) throwable).getCauses()) {
					if (ret != "")
						ret += "\nCaused by: ";
					ret += thr2.toString();
					ret += "\n  at " + getMessage(thr2);
				}
			} else {
				if (ret != "")
					ret += "\nCaused by: ";
				ret += throwable.toString();
				for (StackTraceElement sTE : throwable.getStackTrace())
					ret += "\n  at " + sTE;
			}
			throwable = throwable.getCause();
		}

		return ret;
	}

	/**
	 * @param e
	 */
	public void createReport(Throwable throwable) {
		this.throwable = throwable;
	}

	public void setActive(Boolean value) {
		this.isActive = value;
	}

}
