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

import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author svandecappelle
 * @since 11 oct. 2013
 * @version 1.0.0
 * 
 * 
 */
public class ReporterDisplayImpl extends SimplePanel implements ReporterDisplay {

	private Button sendReport;
	private Button cancelReport;
	private HTML code;
	private FlowPanel content;
	private DisclosurePanel detailPanel;

	/**
	 * Default constructor
	 * 
	 */
	public ReporterDisplayImpl() {
		this.code = new HTML();

		this.content = new FlowPanel();
		this.content.setStylePrimaryName("report-popup");
		this.content.addStyleName("modal");

		HTML head = new HTML("<h3>An error had been caught</h3>");
		HTML desc = new HTML("<p>You can report this error or simply ignore it.</p>");

		this.detailPanel = new DisclosurePanel("Detail");
		this.detailPanel.setWidth("100%");
		FlowPanel descReport = new FlowPanel();
		this.detailPanel.add(descReport);

		this.content.add(head);
		this.content.add(desc);
		this.content.add(this.detailPanel);

		descReport.add(this.code);
		descReport.setStylePrimaryName("stack");
		this.code.setStylePrimaryName("all");

		this.setWidget(this.content);

		this.sendReport = new Button("Send report");
		this.cancelReport = new Button("Cancel");

		FlowPanel actions = new FlowPanel();
		actions.add(this.sendReport);
		actions.add(this.cancelReport);
		actions.getElement().getStyle().setTextAlign(TextAlign.RIGHT);

		this.sendReport.setStylePrimaryName("button");
		this.cancelReport.setStylePrimaryName("button");

		this.content.add(actions);
	}

	@Override
	public HasClickHandlers getReportClickHandler() {
		return sendReport;
	}

	@Override
	public HasClickHandlers getCancelClickHandler() {
		return cancelReport;
	}

	@Override
	public HasOpenHandlers<DisclosurePanel> getDetailOpenHandler() {
		return detailPanel;
	}

	@Override
	public HasCloseHandlers<DisclosurePanel> getDetailCloseHandler() {
		return detailPanel;
	}

	@Override
	public void setContent(String content) {
		this.code.setHTML(SafeHtmlUtils.fromTrustedString("<pre>" + content + "</pre>"));
	}

	@Override
	public void show() {
		this.content.addStyleName("show");
	}

	@Override
	public void hide() {
		super.removeStyleName("show");
	}

	@Override
	public void hideDetail() {
		this.content.getElement().getStyle().setTop(50, Unit.PCT);
	}

	@Override
	public void showDetail() {
		this.content.getElement().getStyle().setTop(70, Unit.PCT);
	}
}
