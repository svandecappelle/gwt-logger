package com.mizore.gwt.logging.client.standard.starter.presenter;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mizore.gwt.logging.client.console.events.ShowLogEvent;
import com.mizore.gwt.logging.client.standard.starter.StandardViewConfigurationsI;
import com.mizore.gwt.logging.client.standard.starter.interfaces.StarterDisplay;

@Singleton
public class StarterPresenter extends WidgetPresenter<StarterDisplay> {

	private StandardViewConfigurationsI configurations;

	@Inject
	public StarterPresenter(StarterDisplay display, EventBus eventBus, StandardViewConfigurationsI configurations) {
		super(display, eventBus);
		this.configurations = configurations;
	}

	@Override
	protected void onBind() {
		if (configurations.isActivated()) {
			this.display.getHasClickHandler().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					eventBus.fireEvent(new ShowLogEvent());
				}
			});
		}
	}

	@Override
	protected void onUnbind() {
		// TODO Unbind here your events.

	}

	@Override
	protected void onRevealDisplay() {
		if (configurations.isActivated()) {
			RootLayoutPanel.get().add(this.display.asWidget());
			RootLayoutPanel.get().setWidgetTopHeight(this.display.asWidget(), 10, Unit.PX, 16, Unit.PX);
			RootLayoutPanel.get().setWidgetRightWidth(this.display.asWidget(), 10, Unit.PX, 16, Unit.PX);
		}
	}

}
