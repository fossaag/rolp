/**
 * Copyright (c) 2013, 2014 Frank Kaddereit, Anne Lachnitt, http://www.fossa.de/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.fossa.vaadin.ui;

import org.fossa.vaadin.FossaApplication;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class FossaBooleanDialog extends FossaWindow implements ClickListener {

	private static final long serialVersionUID = 106870422350837322L;
	
	protected Button buttonTrue;
	protected Button buttonFalse;

	private boolean decision;

	protected CustomLayout layout;
	
	public FossaBooleanDialog(FossaApplication app, String captionWindow, String dialogMessage, String captionTrue, String captionFalse) {
		super(app);
		
		setStyleName("vm");
		setCaption(captionWindow);
		setWidth("500px");
		center();		
		
		layout = new CustomLayout("./subWindows/dialogWarnung");
		
		HorizontalLayout buttonBattery = new HorizontalLayout();
		
		buttonTrue = new Button(captionTrue, (ClickListener) this);
		buttonFalse = new Button(captionFalse, (ClickListener) this);
		buttonTrue.setWidth("200px");
		buttonFalse.setWidth("200px");
		buttonBattery.addComponent(buttonTrue);
		buttonBattery.addComponent(buttonFalse);
		buttonBattery.setSpacing(true);
		
		Label messageLabel = new Label(dialogMessage);
		layout.addComponent(messageLabel, "messageLabel");
		layout.addComponent(buttonBattery, "buttonBattery");
		setContent(layout);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == buttonTrue) {
			decision = true;
			close();
		} else if (source == buttonFalse) {
			decision = false;
			close();
		}
	}
	
	@Override
	public void unlockLaso() {
		// TODO Auto-generated method stub
		
	}

	public boolean getDecision() {
		return decision;
	}
	

}
