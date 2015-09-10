package com.mizore.gwt.logging.client.console.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoggerUIWidget extends VerticalPanel {

	private final HTML resizeIcon;
	private final ScrollPanelWithMinSize scrollPanel;
	private VerticalPanel logArea;
	private HTML titleBar;

	private Button closeButton;
	private Button maximizeButton;
	private Button clearButton;
	private Button configureButton;

	public LoggerUIWidget() {
		HTML titleBar = new HTML("<center><b>Console</b></center>");
		titleBar.addStyleName("Vk-LoggingConsoleHeader");
		this.setTitleBar(titleBar);
		add(getTitleBar());

		scrollPanel = new ScrollPanelWithMinSize();
		add(getScrollPanel());

		setLogArea(new VerticalPanel());
		getScrollPanel().setWidget(getLogArea());
		getScrollPanel().setPixelSize(800, 400);

		HorizontalPanel bottomBar = new HorizontalPanel();
		add(bottomBar);

		FlowPanel buttonsPanel = new FlowPanel();

		maximizeButton = new Button("Minimize");
		maximizeButton.setStylePrimaryName("button");
		buttonsPanel.add(maximizeButton);

		closeButton = new Button("Close");
		closeButton.setStylePrimaryName("button");
		buttonsPanel.add(closeButton);

		clearButton = new Button("Clear");
		clearButton.setStylePrimaryName("button");
		buttonsPanel.add(clearButton);

		configureButton = new Button("Configure");
		configureButton.setStylePrimaryName("button");
		buttonsPanel.add(configureButton);

		bottomBar.add(buttonsPanel);
		bottomBar.setWidth("100%");
		bottomBar.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);

		resizeIcon = new HTML("<div style='font-size:200%; line-height:75%'>\u21F2</div>");
		resizeIcon.addStyleName("Vk-LoggingConsoleResize");
		getResizeIcon().setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		bottomBar.add(getResizeIcon());

	}

	public VerticalPanel getLogArea() {
		return logArea;
	}

	public void setLogArea(VerticalPanel logArea) {
		this.logArea = logArea;
	}

	public ScrollPanelWithMinSize getScrollPanel() {
		return scrollPanel;
	}

	public HTML getTitleBar() {
		return titleBar;
	}

	public void setTitleBar(HTML titleBar) {
		this.titleBar = titleBar;
	}

	public HTML getResizeIcon() {
		return resizeIcon;
	}

	public Button getCloseButton() {
		return closeButton;
	}

	public Button getMinimizeMaximizeButton() {
		return maximizeButton;
	}

	public Button getConfigureButton() {
		return configureButton;
	}

	/**
	 * @return
	 */
	public Button getClearButton() {
		return clearButton;
	}
}
