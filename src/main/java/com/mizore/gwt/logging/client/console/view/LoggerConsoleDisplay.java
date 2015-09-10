package com.mizore.gwt.logging.client.console.view;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasWidgets;

public interface LoggerConsoleDisplay extends WidgetDisplay, HasWidgets {

	/**
     * 
     */
	void setVisible(boolean isVisible);

	void clearLogs();

	/**
	 * @return
	 */
	HasClickHandlers getHasCloseClickHandler();

	/**
	 * @return
	 */
	HasClickHandlers getHasMinimizeMaximizeClickHandler();
	
	/**
	 * @return
	 */
	HasClickHandlers getHasConfigureClickHandlers();

	/**
     * 
     */
	void maximize();

	/**
     * 
     */
	void minimize();

	/**
	 * @return
	 */
	HasClickHandlers getHasClearClickHandler();

	void center();

}
