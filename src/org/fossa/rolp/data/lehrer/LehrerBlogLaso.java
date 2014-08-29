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

package org.fossa.rolp.data.lehrer;

import java.util.Date;
import java.util.List;

import org.fossa.rolp.util.DateUtil;
import org.fossa.vaadin.laso.FossaLaso;

public class LehrerBlogLaso extends FossaLaso {
	
	public static final String ZEITSTEMPEL_COLUMN = "zeitstempel";
	
	private LehrerBlogPojo blog;
	
	public LehrerBlogLaso() {
		blog = new LehrerBlogPojo();
	}
	
	public LehrerBlogLaso(LehrerBlogPojo lehrerBlogPojo) {
		this.blog = lehrerBlogPojo;
	}
	
	public LehrerBlogPojo getPojo() {
		return blog;		
	}
	
	public Long getId() {
		return blog.getId();
	}

	public void setId(Long id) {
		blog.setId(id);
		writeToDatabase();
	}
	
	public LehrerPojo getLehrer(){
		return blog.getLehrer();
	}
	
	public void setLehrer(LehrerPojo lehrer) {
		blog.setLehrer(lehrer);
		writeToDatabase();
	}
	
	public String getEreignis(){
		return blog.getEreignis();
	}
	
	public void setEreignis(String ereignis) {
		blog.setEreignis(ereignis);
		writeToDatabase();
	}
	
	public Date getTimestamp(){
		return blog.getTimestamp();
	}
	
	public void setTimestamp(Date timestamp) {
		blog.setTimestamp(timestamp);
		writeToDatabase();
	}
	
	public String getZeitstempel() {
		return DateUtil.showDateString(getTimestamp());
	}
	
	@Override
	protected void writeToDatabase() {
		writeToDatabase(blog);
	}

	@SuppressWarnings("unchecked")
	public static List<LehrerBlogPojo> getAll() {
		return (List<LehrerBlogPojo>) getAll(LehrerBlogPojo.class);
	}
}
