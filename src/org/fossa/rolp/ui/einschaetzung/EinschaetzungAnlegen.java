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

package org.fossa.rolp.ui.einschaetzung;

import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaWindow;
import org.fossa.vaadin.ui.exception.FossaLasoLockedException;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;

public class EinschaetzungAnlegen extends FossaWindow implements Button.ClickListener {
	
	private static final long serialVersionUID = -3028549054279961927L;
	
	private EinschaetzungAnlegenForm formEinschaetzungAnlegen;
	private EinschaetzungLaso einschaetzungLaso;
	private FossaLaso lasoToBeLocked;
	private Button close;

	public EinschaetzungAnlegen (FossaApplication app, EinschaetzungLaso einschaetzung, String caption, FossaLaso lasoToBeLocked) throws FossaLasoLockedException{
		super(app);
		this.einschaetzungLaso = einschaetzung;
		this.lasoToBeLocked = lasoToBeLocked;
		if (lasoToBeLocked != null) {
			if (lasoToBeLocked.isLocked()) {
				throw new FossaLasoLockedException();
			} else {
				lasoToBeLocked.lock();
			}
		}
		setCaption(" - " + caption + " - ");
		setWidth("700px");
		center();
		CustomLayout layout = new CustomLayout("./subWindows/formAnlegen");
		setContent(layout);

		getEinschaetzungAnlegenForm();
		layout.addComponent(formEinschaetzungAnlegen, "form");
	}
	
	private EinschaetzungAnlegenForm getEinschaetzungAnlegenForm() {
		if (formEinschaetzungAnlegen == null) {
			formEinschaetzungAnlegen = new EinschaetzungAnlegenForm();
			if (einschaetzungLaso.getId() == null) {
				formEinschaetzungAnlegen.addTemporaryItem(einschaetzungLaso);
			} else {
				formEinschaetzungAnlegen.setEinschaetzungAnlegen(einschaetzungLaso);
			}
		}
		return formEinschaetzungAnlegen;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == close) {
			close();
			}
		}

	@Override
	public void unlockLaso() {
		if (einschaetzungLaso != null && !formEinschaetzungAnlegen.isReadOnly()) {
			((FossaLaso) einschaetzungLaso).unlock();
		}
		if (lasoToBeLocked != null) {
			lasoToBeLocked.unlock();
		}
	}

}
