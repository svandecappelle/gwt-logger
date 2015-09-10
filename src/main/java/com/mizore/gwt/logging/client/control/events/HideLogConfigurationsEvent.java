package com.mizore.gwt.logging.client.control.events;

import com.google.gwt.event.shared.GwtEvent;

public class HideLogConfigurationsEvent extends GwtEvent<HideLogConfigurationsHandler> {

	private static Type<HideLogConfigurationsHandler> TYPE;

	public static Type<HideLogConfigurationsHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<HideLogConfigurationsHandler>();
		}
		return TYPE;
	}

	@Override
	protected void dispatch(HideLogConfigurationsHandler handler) {
		handler.onHideLog(this);
	}

	@Override
	public GwtEvent.Type<HideLogConfigurationsHandler> getAssociatedType() {
		return getType();
	}

}
