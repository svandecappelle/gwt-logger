package com.mizore.gwt.logging.client.standard.starter.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mizore.gwt.logging.client.standard.starter.interfaces.StarterDisplay;
import com.mizore.gwt.logging.client.standard.starter.resources.StarterResources;

@Singleton
public class StarterViewImpl extends Image implements StarterDisplay {

	@Inject
	public StarterViewImpl(StarterResources resources) {
		if (resources.css() != null) {
			resources.css().ensureInjected();
			super.addStyleName(resources.css().starter());
			super.setResource(resources.starterImage());
		}
	}

	@Override
	public HasClickHandlers getHasClickHandler() {
		return this;
	}

}
