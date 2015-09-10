package com.mizore.gwt.logging.client.control.view.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.logging.client.DevelopmentModeLogHandler;
import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.logging.client.SimpleRemoteLogHandler;
import com.google.gwt.logging.client.SystemLogHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.UIObject;
import com.mizore.gwt.logging.client.LoggingConfigurationsImpl;
import com.mizore.gwt.logging.client.standard.starter.resources.StarterCss;
import com.mizore.gwt.logging.client.standard.starter.resources.StarterResources;

/**
 * A section allowing the user to enable and disable the different handlers
 * attached to the Root Logger.
 */
public class HandlerController {

    private class CheckboxHandler implements ValueChangeHandler<Boolean> {
        private HasValue<Boolean> checkbox;
        private Handler handler;

        public CheckboxHandler(HasValue<Boolean> checkbox, Handler handler) {
            this.checkbox = checkbox;
            this.handler = handler;
        }

        public void onValueChange(ValueChangeEvent<Boolean> event) {
            LoggingConfigurationsImpl.getRootLogger().config("Logger " + handler.getClass().getName() + " toggle " + (event.getValue() ? "[ON]" : "[OFF]"));
            if (checkbox.getValue()) {
                LoggingConfigurationsImpl.getRootLogger().addHandler(handler);
            } else {
                LoggingConfigurationsImpl.getRootLogger().removeHandler(handler);
            }
        }
    }

    Logger LOGGER = Logger.getLogger("Test");

    private CustomCheckBox consoleCheckbox;
    private CustomCheckBox devmodeCheckbox;
    private CustomCheckBox popupCheckbox;
    private CustomCheckBox systemCheckbox;
    private CustomCheckBox remoteCheckbox;
    private CustomCheckBox bugReporterCheckbox;

    private Map<String, Handler> handlers;
    private Panel panel;
    private Label title;

    public HandlerController() {
        StarterResources starterResouces = GWT.create(StarterResources.class);
        StarterCss css = starterResouces.css();

        this.panel = new FlowPanel();
        this.title = new Label("Logging controllers");
        this.title.setStylePrimaryName(css.title());

        this.systemCheckbox = new CustomCheckBox("S", "System Handler");
        this.consoleCheckbox = new CustomCheckBox("C", "Console Handler");
        this.devmodeCheckbox = new CustomCheckBox("D", "Dev Mode Handler");
        this.popupCheckbox = new CustomCheckBox("P", "Popup Handler");
        this.remoteCheckbox = new CustomCheckBox("S", "Simple Remote Handler");
        this.bugReporterCheckbox = new CustomCheckBox("BR", "BugReporter");

        this.panel.add(title);
    }

    public void drawLogHandlers() {
        this.panel.add(systemCheckbox);
        this.panel.add(consoleCheckbox);
        this.panel.add(devmodeCheckbox);
        this.panel.add(popupCheckbox);
        this.panel.add(remoteCheckbox);
        this.panel.add(bugReporterCheckbox);

        Handler[] handlersArray = LoggingConfigurationsImpl.getRootLogger().getHandlers();

        this.handlers = new HashMap<String, Handler>();
        if (handlersArray != null) {
            for (Handler h : handlersArray) {
                this.handlers.put(h.getClass().getName(), h);
            }
        }

        setupHandler(SystemLogHandler.class, systemCheckbox);
        setupHandler(ConsoleLogHandler.class, consoleCheckbox);
        setupHandler(DevelopmentModeLogHandler.class, devmodeCheckbox);
        setupHandler(HasWidgetsLogHandler.class, popupCheckbox);
        setupHandler(SimpleRemoteLogHandler.class, remoteCheckbox);

        remoteCheckbox.setValue(false, true);
        bugReporterCheckbox.setValue(LoggingConfigurationsImpl.isReportActivated(), false);
    }

    public HasValueChangeHandlers<Boolean> getBugReporterCheckbox() {
        return bugReporterCheckbox;
    }

    public Panel getDisplay() {
        return panel;
    }

    void setupHandler(Class<?> clazz, HasValue<Boolean> checkbox) {
        Handler h = handlers.get(clazz.getName());
        if (h == null) {
            ((UIObject) checkbox).setVisible(false);
        } else {
            checkbox.setValue(true);
            checkbox.addValueChangeHandler(new CheckboxHandler(checkbox, h));
        }
    }
}
