package com.mizore.gwt.logging.client.control.view.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.DropdownButton;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Row;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Singleton;
import com.mizore.gwt.logging.client.control.exception.FakeException;
import com.mizore.gwt.logging.client.standard.starter.resources.StarterCss;
import com.mizore.gwt.logging.client.standard.starter.resources.StarterResources;

/**
 * A section allowing the user to change the level of a logger and log messages
 * to that logger.
 */
@Singleton
public class OneLoggerController {
    private static final int ALL_LEVEL = 8;
    private DropdownButton levelList;
    private DropdownButton loggerAvailableList;
    private DropdownButton loggersTypesList;

    private Button logButton;
    private Button triggerExceptionButton;

    private VerticalPanel panel;
    private Logger logger;
    private List<String> levelListKeys;

    public OneLoggerController() {
        StarterResources starterResouces = GWT.create(StarterResources.class);
        StarterCss css = starterResouces.css();

        this.levelListKeys = new ArrayList<String>();
        levelListKeys.add("OFF");
        levelListKeys.add("SEVERE");
        levelListKeys.add("WARNING");
        levelListKeys.add("INFO");
        levelListKeys.add("CONFIG");
        levelListKeys.add("FINE");
        levelListKeys.add("FINER");
        levelListKeys.add("FINEST");
        levelListKeys.add("ALL");

        this.panel = new VerticalPanel();
        this.panel.setWidth("100%");

        this.levelList = new DropdownButton();
        this.loggerAvailableList = new DropdownButton();
        this.loggersTypesList = new DropdownButton();
        this.logButton = new Button("Write a log");
        this.triggerExceptionButton = new Button("Trigger an exception");

        FlowPanel allConfigs = new FlowPanel();
        allConfigs.getElement().getStyle().setMarginLeft(20, Unit.PX);
        Row configuration = new Row();
        allConfigs.add(configuration);
        this.panel.add(allConfigs);

        // # Logger conf
        Label title = new Label("Logger configuration");
        title.setStylePrimaryName(css.title());
        configuration.add(title);

        // ## logger type
        Column loggerConfColumnLabel = new Column(1);
        InlineLabel loggerLabel = new InlineLabel("Logger");
        loggerConfColumnLabel.add(loggerLabel);
        loggerLabel.setStylePrimaryName(css.label());
        configuration.add(loggerConfColumnLabel);

        Column loggerConfColumnWidget = new Column(2);
        loggerConfColumnWidget.add(loggerAvailableList);
        configuration.add(loggerConfColumnWidget);

        // ## level type
        InlineLabel levelLabel = new InlineLabel("Level");
        levelLabel.setStylePrimaryName(css.label());
        
        Column levelLabelColumn  = new Column(1);
        levelLabelColumn.add(levelLabel);
        configuration.add(levelLabelColumn);
        
        Column levelListWidgetColumn = new Column(2);
        levelListWidgetColumn.add(levelList);
        configuration.add(levelListWidgetColumn);

        // # Testing
        Row configurationTesting = new Row();
        allConfigs.add(configurationTesting);

        Label testingTitle = new Label("Testing configuration");
        testingTitle.setStylePrimaryName(css.title());
        configurationTesting.add(testingTitle);

        // ### Log type
        InlineLabel logTypeLabel = new InlineLabel("Log-type");
        logTypeLabel.setStylePrimaryName(css.label());

        Row testing = new Row();
        allConfigs.add(testing);

        Label writeLabel = new Label("Test logging");
        writeLabel.setStylePrimaryName(css.label());
        testing.add(writeLabel);

        // ### Write log
        
        Column testingLogColumn = new Column(2);
        testingLogColumn.add(logButton);
        testing.add(testingLogColumn);

        Column testingTypeLabelColumn = new Column(1);
        testingTypeLabelColumn.add(logTypeLabel);
        testing.add(testingTypeLabelColumn);

        Column testingTypeListColumn = new Column(2);
        testingTypeListColumn.add(loggersTypesList);
        testing.add(testingTypeListColumn);

        // ## Exception testing
        Row testException = new Row();
        allConfigs.add(testException);
        
        Label testingExceptionTitle = new Label("Test exception");
        testingExceptionTitle.setStylePrimaryName(css.label());
        testException.add(testingExceptionTitle);
        Column testingExceptionButtonColumn = new Column(4);
        testingExceptionButtonColumn.add(triggerExceptionButton);
        testException.add(testingExceptionButtonColumn);

        this.loggerAvailableList.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                loggerAvailableList.setText(loggerAvailableList.getLastSelectedNavLink().getText().trim());
                logger = Logger.getLogger(loggerAvailableList.getText().trim());
                if (logger.getLevel() == null) {
                    // ALL Level
                    levelList.setText(levelListKeys.get(levelListKeys.size() - 1));
                } else {
                    levelList.setText(logger.getLevel().getName());
                }
            }
        });

        this.levelList.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                levelList.setText(levelList.getLastSelectedNavLink().getText().trim());
                Level level = Level.parse(levelList.getText().trim());
                logger.log(Level.INFO, "Setting level to: " + level.getName());
                logger.setLevel(level);
            }
        });

        this.loggersTypesList.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                loggersTypesList.setText(loggersTypesList.getLastSelectedNavLink().getText().trim());
            }
        });

        this.logButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Level level = Level.parse(loggersTypesList.getText().trim());
                logger.log(level, "This is a client log message");
            }
        });

        this.triggerExceptionButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                throw new FakeException();
            }
        });

        this.addLoggerType("ROOT");
        this.logger = Logger.getLogger(loggerAvailableList.getText().trim());
        this.addLevelButtons();
        this.addLogButtons();
    }

    public void addLoggerType(String loggerType) {
        if (loggerAvailableList.getText().trim().isEmpty()){
            loggerAvailableList.setText(loggerType);
        }
        loggerAvailableList.add(new NavLink(loggerType));
    }

    public Panel getDisplay() {
        return panel;
    }

    private void addLevelButtons() {
        String currentLevel = this.getLevel(logger);
        levelList.setText(currentLevel);
        for (String level : levelListKeys) {
            levelList.add(new NavLink(level));
        }
    }

    private void addLogButtons() {
        String currentLevel = this.getLevel(logger);
        loggersTypesList.setText(currentLevel);
        for (String level : levelListKeys) {
            loggersTypesList.add(new NavLink(level));
        }
    }

    private String getLevel(Logger logger) {
        if (logger != null) {
            if (logger.getLevel() != null) {
                return logger.getLevel().getName();
            }
            return getLevel(logger.getParent());
        }
        return "";
    }
}
