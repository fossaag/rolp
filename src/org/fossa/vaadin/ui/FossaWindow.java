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
import org.vaadin.jouni.animator.client.ui.VAnimatorProxy.AnimType;

import com.vaadin.ui.Window;

public abstract class FossaWindow extends Window {

	private static final long serialVersionUID = -8004638542577577422L;
	
	public FossaWindow(FossaApplication app) {
		super();
		setModal(true);
		removeCloseShortcut();
		app.getAnimator().animate(this, AnimType.FADE_IN).setDuration(250).setDelay(0);
		addListener((Window.CloseListener) app);
	}

	public void close() {
		super.close();
		removeAllActionHandlers();
	}
	
	public abstract void unlockLaso();
}
