package com.mizore.gwt.logging.client.console.view;

import java.util.Iterator;

import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

/**
 * @author svandecappelle
 * @since 7 oct. 2013
 * @version 1.0.0 A simple popup to show log messages, which can be resized,
 *          minimized, and dragged to a different location.
 * 
 */
@Singleton
public class LoggingConsolePopup extends PopupPanel implements LoggerConsoleDisplay {

	/**
	 * Logger console.
	 */
	private LoggerUIWidget widget;

	/**
	 * Default constructor.
	 * 
	 */
	public LoggingConsolePopup() {
		super.addStyleName("console");
		widget = new LoggerUIWidget();

		// simple display resize and move handlers.
		new WindowMoveHandler(widget.getTitleBar());
		new WindowResizeHandler(widget.getResizeIcon());

		super.setWidget(widget);
	}

	@Override
	public HasClickHandlers getHasCloseClickHandler() {
		return widget.getCloseButton();
	}

	@Override
	public HasClickHandlers getHasMinimizeMaximizeClickHandler() {
		return widget.getMinimizeMaximizeButton();
	}

	@Override
	public HasClickHandlers getHasClearClickHandler() {
		return widget.getClearButton();
	}

	@Override
	public void maximize() {
		widget.getScrollPanel().setVisible(true);
		widget.getResizeIcon().setVisible(true);
		widget.getClearButton().setVisible(true);
		widget.getMinimizeMaximizeButton().setText("Minimize");
	}

	@Override
	public void minimize() {
		widget.getMinimizeMaximizeButton().setText("Maximize");
		widget.getScrollPanel().setVisible(false);
		widget.getResizeIcon().setVisible(false);
		widget.getClearButton().setVisible(false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void add(Widget w) {
		widget.getLogArea().add(w);
		widget.getScrollPanel().setScrollPosition(widget.getScrollPanel().getElement().getScrollHeight());
	}

	@Override
	public void setWidget(Widget w) {
		widget.getLogArea().clear();
		add(w);
	}

	/**
	 * Handles the logic to track click-drag movements with the mouse.
	 */
	private abstract class MouseDragHandler implements MouseMoveHandler, MouseUpHandler, MouseDownHandler {
		protected boolean dragging = false;
		protected Widget dragHandle;
		protected int dragStartX;
		protected int dragStartY;

		public MouseDragHandler(Widget dragHandle) {
			this.dragHandle = dragHandle;
			HasAllMouseHandlers hamh = (HasAllMouseHandlers) dragHandle;
			hamh.addMouseDownHandler(this);
			hamh.addMouseUpHandler(this);
			hamh.addMouseMoveHandler(this);
		}

		public abstract void handleDrag(int absX, int absY);

		public void onMouseDown(MouseDownEvent event) {
			dragging = true;
			DOM.setCapture(dragHandle.getElement());
			dragStartX = event.getClientX();
			dragStartY = event.getClientY();
			DOM.eventPreventDefault(DOM.eventGetCurrentEvent());
		}

		public void onMouseMove(MouseMoveEvent event) {
			if (dragging) {
				handleDrag(event.getClientX() - dragStartX, event.getClientY() - dragStartY);
				dragStartX = event.getClientX();
				dragStartY = event.getClientY();
			}
		}

		public void onMouseUp(MouseUpEvent event) {
			dragging = false;
			DOM.releaseCapture(dragHandle.getElement());
		}
	}

	private class WindowMoveHandler extends MouseDragHandler {
		public WindowMoveHandler(Widget dragHandle) {
			super(dragHandle);
		}

		@Override
		public void handleDrag(int absX, int absY) {
			Widget moveTarget = LoggingConsolePopup.this;
			RootPanel.get().setWidgetPosition(moveTarget, moveTarget.getAbsoluteLeft() + absX, moveTarget.getAbsoluteTop() + absY);
		}
	}

	private class WindowResizeHandler extends MouseDragHandler {
		public WindowResizeHandler(Widget dragHandle) {
			super(dragHandle);
		}

		@Override
		public void handleDrag(int absX, int absY) {
			widget.getScrollPanel().incrementPixelSize(absX, absY);
		}
	}

	@Override
	public void clearLogs() {
		widget.getLogArea().clear();
	}

	@Override
	public void clear() {
		widget.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return widget.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return widget.remove(w);
	}

	@Override
	public HasClickHandlers getHasConfigureClickHandlers() {
		return widget.getConfigureButton();
	}

}
