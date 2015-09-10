package com.mizore.gwt.logging.client.console.presenter;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mizore.gwt.logging.client.LoggingConfigurationsImpl;
import com.mizore.gwt.logging.client.console.events.ShowLogEvent;
import com.mizore.gwt.logging.client.console.events.ShowLogHandler;
import com.mizore.gwt.logging.client.console.view.LoggerConsoleDisplay;
import com.mizore.gwt.logging.client.control.events.ShowLogConfigurationsEvent;

/**
 * @author svandecappelle
 * @since 7 oct. 2013
 * @version 1.0.0
 * 
 *          Log console Presenter.
 */
@Singleton
public class LoggerConsolePresenter extends WidgetPresenter<LoggerConsoleDisplay> {

	private boolean isMaximized = true;
	private boolean initialShowing = true;

	@Inject
	public LoggerConsolePresenter(LoggerConsoleDisplay display, EventBus eventBus) {
		super(display, eventBus);
		LoggingConfigurationsImpl.getRootLogger().addHandler(new HasWidgetsLogHandler(display));
	}

	@Override
	protected void onBind() {
		registerHandler(eventBus.addHandler(ShowLogEvent.getType(), new ShowLogHandler() {

			public void onShowLog(ShowLogEvent showLogEvent) {
				if (initialShowing) {
					display.center();
					initialShowing = false;
				} else {
					display.setVisible(true);
				}
			}
		}));

		registerHandler(display.getHasMinimizeMaximizeClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				minimizeMaximizeView();
			}
		}));

		registerHandler(display.getHasClearClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.clearLogs();
			}
		}));

		registerHandler(display.getHasCloseClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.setVisible(false);
			}
		}));

		registerHandler(display.getHasConfigureClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ShowLogConfigurationsEvent());
			}
		}));

		// This is kind of hacky, but we want the user to see this explanation
		// in the popup when the page starts up, so we pull out the popup
		// handler
		// and publish a message directly to it. Most applications should not
		// do this.
		Handler[] handlers = Logger.getLogger("").getHandlers();
		if (handlers != null) {
			for (Handler h : handlers) {
				if (h instanceof HasWidgetsLogHandler) {
					String msg = "This popup can be resized, moved and minimized";
					h.publish(new LogRecord(Level.INFO, msg));
				}
			}
		}
	}

	/**
     * 
     */
	protected void minimizeMaximizeView() {
		if (isMaximized) {
			display.minimize();
		} else {
			display.maximize();
		}
		isMaximized = !isMaximized;
	}

	@Override
	protected void onUnbind() {

	}

	@Override
	protected void onRevealDisplay() {

	}

}
