package com.mizore.gwt.logging.client.control.view.controllers;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A section explaining how to set logger levels and containing controllers for
 * 3 specific loggers.
 */
@Singleton
public class LoggerController {

    private SimplePanel rootControls;

    private OneLoggerController loggerController;

    @Inject
    public LoggerController(OneLoggerController loggerController) {
        this.loggerController = loggerController;
        this.rootControls = new SimplePanel();
        this.rootControls.setWidget(this.loggerController.getDisplay());
    }

    public IsWidget getDisplay() {
        return this.rootControls;
    }

    public void addLoggerType(String loggerType) {
        loggerController.addLoggerType(loggerType);
    }
}
