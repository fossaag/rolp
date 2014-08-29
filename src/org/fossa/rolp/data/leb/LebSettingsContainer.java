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

package org.fossa.rolp.data.leb;

import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class LebSettingsContainer extends BeanItemContainer<LebSettingsLaso> implements Serializable {
	
	private static final long serialVersionUID = 1020983462212093915L;

	public static final String[] NATURAL_FORM_ORDER = new String[] {
		LebSettingsPojo.ZEUGNISAUSGABEDATUM_COLUMN,
		LebSettingsPojo.HALBJAHR_COLUMN,
		LebSettingsPojo.ANZAHL_ERSTE_KLASSEN_COLUMN,
		LebSettingsPojo.LETZTE_KLASSENSTUFE_COLUMN,
	};

	private static LebSettingsContainer lebsettingsContainer;

	private LebSettingsContainer() {
		super(LebSettingsLaso.class);
	}
	
	public static LebSettingsContainer getInstance() {
		if (lebsettingsContainer == null) {
			lebsettingsContainer = new LebSettingsContainer();
			for (LebSettingsPojo lebsettingsPojo: LebSettingsLaso.getAll()) {
				LebSettingsLaso lebsettingsLaso = new LebSettingsLaso(lebsettingsPojo);
				lebsettingsContainer.addBean(lebsettingsLaso);
			}
		}
		return lebsettingsContainer;		
	}
	
	public static LebSettingsLaso getLebSettings() {
		LebSettingsLaso lebSettingsLaso = getInstance().firstItemId();
		if (lebSettingsLaso == null) {
			return new LebSettingsLaso();
		} else {
			return lebSettingsLaso;
		}
	}
}