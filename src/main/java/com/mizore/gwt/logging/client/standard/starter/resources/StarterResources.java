package com.mizore.gwt.logging.client.standard.starter.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;

public interface StarterResources extends ClientBundle {

	@Source("main.css")
	StarterCss css();

	@Source("console.png")
	@ImageOptions(width = 16)
	ImageResource starterImage();

}
