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

import java.util.Arrays;
import java.util.List;

import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.exception.FossaFormInvalidException;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;

public abstract class FossaForm extends Form implements ClickListener {
	
	private static final long serialVersionUID = 667889335770342183L;

	protected Button close = new Button("Schlieﬂen", (ClickListener) this);
	protected Button save = new Button("Speichern", (ClickListener) this);
	protected Button cancel = new Button("Abbrechen", (ClickListener) this);
	protected Button edit = new Button("Bearbeiten", (ClickListener) this);
	private boolean newFormMode = false;
	protected FossaLaso fossaLaso = null;

	public FossaForm() {
		setWriteThrough(false);
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(cancel);
		footer.addComponent(edit);
		footer.addComponent(close);
		footer.setVisible(false);
		setFooter(footer);
		super.setReadOnly(true);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		if (source == save) {
			try {
				saveFossaForm();
			} catch (FossaFormInvalidException e) {
				getWindow().showNotification(e.getMessage());
			}
		} else if (source == cancel) {
			if (newFormMode) {
				setItemDataSource(new BeanItem<FossaLaso>(fossaLaso));
				closeWindow();
				newFormMode = true;
				return;
			} else {
				discard();
				((FossaLaso) fossaLaso).unlock();
			}
			setReadOnly(true);
		} else if (source == edit) {
			editFossaForm();
		} else if (source == close) {
			closeWindow();
		}
	}

	protected void closeWindow() {
		((FossaWindow) getWindow()).close();
		removeAllProperties();
	}

	public void setItemDataSource(Item newDataSource, Object[] order) {
		newFormMode = false;
		if (newDataSource != null) {
			List<Object> orderedProperties = Arrays
					.asList(order);
			super.setItemDataSource(newDataSource, orderedProperties);
			getFooter().setVisible(true);
		} else {
			super.setItemDataSource(null);
			getFooter().setVisible(false);
		}
	}
	private void editFossaForm() {
		setReadOnly(false);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		if (readOnly && !newFormMode && (fossaLaso!=null) && !isReadOnly()) {
			((FossaLaso) fossaLaso).unlock();
		} else if (!readOnly && !newFormMode) {
			if (((FossaLaso) fossaLaso).isLocked()) {
				getWindow().showNotification("LOCKED");
				return;
			} else {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					//doesn't matter
				}
				if (((FossaLaso) fossaLaso).isLocked()) {
					getWindow().showNotification("LOCKED");
					return;
				} else {
					((FossaLaso) fossaLaso).lock();
				}
			}
		}
		super.setReadOnly(readOnly);
		save.setVisible(!readOnly);
		cancel.setVisible(!readOnly);
		edit.setVisible(readOnly);
		close.setVisible(readOnly);
	}
	
	public void saveFossaForm() throws FossaFormInvalidException {
		if (!isValid()) {
			throw new FossaFormInvalidException();
		}
		commit();
		if (newFormMode) {
			setItemDataSource(addItemToContainer(fossaLaso));
			newFormMode = false;
		}
		setReadOnly(true);
	}

	abstract public Item addItemToContainer(FossaLaso fossaLaso);

	public void addTemporaryItem(FossaLaso fossa) {
		fossaLaso = fossa;
		setItemDataSource(new BeanItem<FossaLaso>(fossaLaso));
		newFormMode = true;
		setReadOnly(false);

	}
	
	public void setFossaLaso(FossaLaso fossa) {
		fossaLaso = fossa;
		setItemDataSource(new BeanItem<FossaLaso>(fossaLaso));
		setReadOnly(true);
	}	

}
