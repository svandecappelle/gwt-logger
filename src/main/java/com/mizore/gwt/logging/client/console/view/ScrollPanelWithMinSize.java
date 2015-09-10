package com.mizore.gwt.logging.client.console.view;

import com.google.gwt.user.client.ui.ScrollPanel;

public class ScrollPanelWithMinSize extends ScrollPanel {
	private int minScrollPanelHeight = 100;
	private int minScrollPanelWidth = 100;
	private int scrollPanelHeight;
	private int scrollPanelWidth;

	public void incrementPixelSize(int width, int height) {
		setPixelSize(scrollPanelWidth + width, scrollPanelHeight + height);
	}

	@Override
	public void setPixelSize(int width, int height) {
		super.setPixelSize(scrollPanelWidth = Math.max(width, minScrollPanelWidth), scrollPanelHeight = Math.max(height, minScrollPanelHeight));
	}
}
