package com.mizore.gwt.logging.client.console.events;

import com.google.gwt.event.shared.GwtEvent;

public class ShowLogEvent extends GwtEvent<ShowLogHandler> {

	private static Type<ShowLogHandler> TYPE;

	public static Type<ShowLogHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<ShowLogHandler>();
		}
		return TYPE;
	}

	@Override
	protected void dispatch(ShowLogHandler handler) {
		handler.onShowLog(this);
	}

	@Override
	public GwtEvent.Type<ShowLogHandler> getAssociatedType() {
		return getType();
	}

}
