package com.mizore.gwt.logging.client.control.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.mizore.gwt.apprise.client.Popup;
import com.mizore.gwt.logging.client.control.presenter.LoggingPresenter.LoggingDisplay;
import com.mizore.gwt.logging.client.control.view.controllers.HandlerController;
import com.mizore.gwt.logging.client.control.view.controllers.LoggerController;

/**
 * A page to help users understand logging in GWT.
 */
public class LoggerUiImpl extends Popup implements LoggingDisplay {

    private SimplePanel handlerControls;
    private SimplePanel loggerControls;
    private SimplePanel logOnServerControls;
    private Button showconsoleButton;
    private Button closeConfigurations;
    private HandlerController handlerController;

    @Inject
    public LoggerUiImpl(LoggerController loggerController, HandlerController handlerController, ServerLoggingArea serverLoggerController) {
        this.handlerController = handlerController;

        super.addStyleName("console-config");

        this.handlerControls = new SimplePanel();
        this.loggerControls = new SimplePanel();
        this.logOnServerControls = new SimplePanel();
        this.showconsoleButton = new Button("Show console");

        loggerControls.setWidget(loggerController.getDisplay());
        handlerControls.setWidget(handlerController.getDisplay());
        logOnServerControls.setWidget(serverLoggerController.getDisplay());

        FlowPanel container = new FlowPanel();
        container.add(loggerControls);
        container.add(handlerControls);
        container.add(logOnServerControls);
        this.setWidget(container);

        showconsoleButton.setStylePrimaryName("button");
        closeConfigurations = this.addButton("OK");
    }

    @Override
    public void drawLogHandlers() {
        handlerController.drawLogHandlers();
    }

    @Override
    public HandlerController getHandlerController() {
        return handlerController;
    }

    @Override
    public Button getHasCloseConfigurationsClickHandler() {
        return closeConfigurations;
    }

    @Override
    public HasClickHandlers getHasShowConsoleButtonHandlers() {
        return showconsoleButton;
    }

}
