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

package org.fossa.rolp.util;

import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.schueler.SchuelerPojo;

public class MailcheckUtil {
	public static void checkdate(KlasseLaso klasse)
	{	
	 String schuelerliste = "";
	 for (SchuelerPojo schueler: SchuelerLaso.getAll()){	
		 if((schueler.getKlasse()!=null) && (klasse!=null) && (schueler.getKlasse().getId().equals(klasse.getId()))&& schueler.getSchuelereinschaetzung()== null )
			{
				schuelerliste.concat(schueler.getVorname());
				schuelerliste.concat(schueler.getName());
				schuelerliste.concat("");
				schuelerliste.concat("              ");
			}
		MailUtil.sendmail(klasse.getKlassenlehrer().getMail(), schuelerliste );		}     
			
		}
	}	

