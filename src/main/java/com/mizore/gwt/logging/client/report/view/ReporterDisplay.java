/*
 * Gwt Logger Library 
 * 
 * 
 *
 * 
 * 
 * 
 */
package com.mizore.gwt.logging.client.report.view;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.user.client.ui.DisclosurePanel;

/**
 * @author svandecappelle
 * @since 9 oct. 2013
 * @version 1.0.0
 * 
 * 
 */
public interface ReporterDisplay extends WidgetDisplay {

	/**
     * 
     */
	void show();

	/**
	 * @return
	 */
	HasClickHandlers getReportClickHandler();

	/**
     * 
     */
	void hide();

	/**
	 * @param content
	 */
	void setContent(String content);

	/**
	 * @return
	 */
	HasClickHandlers getCancelClickHandler();

	/**
	 * @return
	 */
	HasOpenHandlers<DisclosurePanel> getDetailOpenHandler();

	/**
	 * @return
	 */
	HasCloseHandlers<DisclosurePanel> getDetailCloseHandler();

	/**
     * 
     */
	void hideDetail();

	/**
     * 
     */
	void showDetail();

}
