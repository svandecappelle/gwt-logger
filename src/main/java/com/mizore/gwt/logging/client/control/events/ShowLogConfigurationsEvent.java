package com.mizore.gwt.logging.client.control.events;

import com.google.gwt.event.shared.GwtEvent;

public class ShowLogConfigurationsEvent extends GwtEvent<ShowLogConfigurationsHandler> {

	private static Type<ShowLogConfigurationsHandler> TYPE;

	public static Type<ShowLogConfigurationsHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<ShowLogConfigurationsHandler>();
		}
		return TYPE;
	}

	@Override
	protected void dispatch(ShowLogConfigurationsHandler handler) {
		handler.onShowLog(this);
	}

	@Override
	public GwtEvent.Type<ShowLogConfigurationsHandler> getAssociatedType() {
		return getType();
	}

}
