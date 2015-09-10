package com.mizore.gwt.logging.client.control.view.controllers;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.mizore.gwt.logging.client.standard.starter.resources.StarterCss;
import com.mizore.gwt.logging.client.standard.starter.resources.StarterResources;

public class CustomCheckBox extends SimplePanel implements HasValue<Boolean> {

    private StarterResources starterResouces = GWT.create(StarterResources.class);
    private StarterCss css;
    private SimplePanel handle;
    private Label textLabel;

    private Boolean value = false;

    public CustomCheckBox(String text, String title) {
        this.css = starterResouces.css();
        this.setStylePrimaryName(css.icon());

        this.handle = new SimplePanel();
        this.handle.setStylePrimaryName(css.handle());
        this.setWidget(handle);

        this.textLabel = new Label(text);
        this.handle.setWidget(textLabel);

        this.addDomHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                setValue(!value, true);
            }
        }, ClickEvent.getType());

        this.setValue(false, false);
        this.setTitle(title);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        setValue(value, false);
    }

    @Override
    public void setValue(Boolean value, boolean fireEvents) {
        this.value = value;
        if (this.value) {
            CustomCheckBox.this.addStyleName(css.on());
            CustomCheckBox.this.removeStyleName(css.red());
            CustomCheckBox.this.addStyleName(css.green());
        } else {
            CustomCheckBox.this.removeStyleName(css.on());
            CustomCheckBox.this.removeStyleName(css.green());
            CustomCheckBox.this.addStyleName(css.red());
        }

        if (fireEvents) {
            ValueChangeEvent.fire(this, value);
        }
    }
}
