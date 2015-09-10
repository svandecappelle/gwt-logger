package com.mizore.gwt.logging.client.control.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mizore.gwt.logging.client.LoggingConfigurationsImpl;
import com.mizore.gwt.logging.client.console.events.ShowLogEvent;
import com.mizore.gwt.logging.client.console.presenter.LoggerConsolePresenter;
import com.mizore.gwt.logging.client.control.events.HideLogConfigurationsEvent;
import com.mizore.gwt.logging.client.control.events.HideLogConfigurationsHandler;
import com.mizore.gwt.logging.client.control.events.ShowLogConfigurationsEvent;
import com.mizore.gwt.logging.client.control.events.ShowLogConfigurationsHandler;
import com.mizore.gwt.logging.client.control.presenter.LoggingPresenter.LoggingDisplay;
import com.mizore.gwt.logging.client.control.view.controllers.HandlerController;
import com.mizore.gwt.logging.client.control.view.controllers.LoggerController;
import com.mizore.gwt.logging.client.report.presenter.BugReporterPresenter;
import com.mizore.gwt.logging.client.standard.starter.presenter.StarterPresenter;

@Singleton
public class LoggingPresenter extends WidgetPresenter<LoggingDisplay> {
    public interface LoggingDisplay extends WidgetDisplay {

        void show();

        /**
         * @return
         */
        HasClickHandlers getHasShowConsoleButtonHandlers();

        /**
		 * 
		 */
        void hide();

        Button getHasCloseConfigurationsClickHandler();

        HandlerController getHandlerController();

        void drawLogHandlers();

    }

    private List<String> loggers;

    @Inject
    private LoggerController controller;

    @Inject
    private BugReporterPresenter reporterPresenter;

    @Inject
    private StarterPresenter starterPresenter;

    protected boolean isConfigurationShowing;

    @Inject
    public LoggingPresenter(LoggingDisplay display, LoggerConsolePresenter consolePresenter, EventBus eventBus) {
        super(display, eventBus);
        loggers = new ArrayList<String>();
        // the easy way to bind the logger Console without known any Logging
        // class on child app.
        consolePresenter.bind();
        Logger.getLogger("").config("Logging presenter setting up from " + GWT.getModuleName());
    }

    @Override
    protected void onBind() {
        starterPresenter.bind();

        registerHandler(eventBus.addHandler(ShowLogConfigurationsEvent.getType(), new ShowLogConfigurationsHandler() {

            public void onShowLog(ShowLogConfigurationsEvent showLogEvent) {
                if (!isConfigurationShowing) {
                    isConfigurationShowing = true;
                    display.show();
                }
            }
        }));

        registerHandler(eventBus.addHandler(HideLogConfigurationsEvent.getType(), new HideLogConfigurationsHandler() {

            public void onHideLog(HideLogConfigurationsEvent showLogEvent) {
                isConfigurationShowing = false;
                display.hide();
            }
        }));

        registerHandler(display.getHasShowConsoleButtonHandlers().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new ShowLogEvent());
            }
        }));

        registerHandler(display.getHasCloseConfigurationsClickHandler().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                isConfigurationShowing = false;
                display.hide();
            }
        }));

        registerHandler(display.getHandlerController().getBugReporterCheckbox().addValueChangeHandler(new ValueChangeHandler<Boolean>() {

            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                reporterPresenter.setActive(event.getValue());
            }
        }));

        if (LoggingConfigurationsImpl.isReportActivated()) {
            reporterPresenter.bind();
        }
    }

    public void addLoggerType(String loggerType) {
        if (!loggers.contains(loggerType)) {
            controller.addLoggerType(loggerType);
        }
    }

    @Override
    protected void onUnbind() {
        if (LoggingConfigurationsImpl.isReportActivated()) {
            reporterPresenter.unbind();
        }

        starterPresenter.unbind();
    }

    @Override
    protected void onRevealDisplay() {
        display.drawLogHandlers();
        starterPresenter.revealDisplay();
    }
}
