package com.mizore.gwt.logging.client.control.view;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.logging.client.TextLogFormatter;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mizore.gwt.logging.client.control.view.controllers.CustomCheckBox;
import com.mizore.gwt.logging.client.service.LoggerService;
import com.mizore.gwt.logging.client.service.LoggerServiceAsync;
import com.mizore.gwt.logging.client.standard.starter.resources.StarterCss;
import com.mizore.gwt.logging.client.standard.starter.resources.StarterResources;

/**
 * A section allowing the user to experiment with server side logging and shared
 * library logging when it is called from server vs client side code.
 */
public class ServerLoggingArea {

    private static final Logger logger = Logger.getLogger("Logger.ServerLoggingArea");

    static {
        logger.setLevel(Level.SEVERE);
        logger.setParent(Logger.getLogger("Logger"));
    }

    public class ServerLogHandler extends Handler {
        private boolean isActivated;

        /**
         * Default constructor
         * 
         */
        public ServerLogHandler() {
            setFormatter(new TextLogFormatter(true));
            setLevel(Level.ALL);
        }

        @Override
        public void close() {
            // No action needed
        }

        @Override
        public void flush() {
            // No action needed
        }

        @Override
        public void publish(LogRecord record) {
            if (LogConfiguration.loggingIsEnabled()) {
                if (isActivated) {
                    // desactivate the stacktrace on serverside (cause
                    // ThrownSerializableException)
                    LogRecord recordClone = new LogRecord(record.getLevel(), record.getMessage());
                    myService.log(recordClone, new AsyncCallback<Void>() {

                        public void onFailure(Throwable caught) {
                            logger.log(Level.SEVERE, "Call to my service failed", caught);
                        }

                        public void onSuccess(Void result) {
                        }
                    });
                }
            }
        }

        /**
         * @return
         */
        public boolean isActivated() {
            return isActivated;
        }

        /**
         * @param isActivated
         *            the isActivated to set
         */
        public void setActivated(boolean isActivated) {
            this.isActivated = isActivated;
        }
    }

    private final LoggerServiceAsync myService = GWT.create(LoggerService.class);

    private Panel panel;
    private ServerLogHandler serverBridgeLogger;
    private CustomCheckBox serverSharedMethodButton;

    public ServerLoggingArea() {
        StarterResources starterResouces = GWT.create(StarterResources.class);
        StarterCss css = starterResouces.css();

        panel = new VerticalPanel();
        this.panel.setWidth("100%");

        serverSharedMethodButton = new CustomCheckBox("PL", "Push logs on Server");

        Label title = new Label("Communication configuration");
        title.setStylePrimaryName(css.title());

        panel.add(title);
        panel.add(serverSharedMethodButton);

        serverSharedMethodButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                serverBridgeLogger.setActivated(event.getValue());
            }
        });

        serverBridgeLogger = new ServerLogHandler();
        Logger.getLogger("").addHandler(serverBridgeLogger);
    }

    public Panel getDisplay() {
        return panel;
    }

    @UiHandler("serverSharedMethodButton")
    void handleServerSharedMethodButton(ValueChangeEvent<Boolean> isServerPushLogger) {

    }
}
