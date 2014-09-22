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

package org.fossa.rolp.ui.lehrer.lehrerblog;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.rolp.data.lehrer.lehrerblog.LehrerBlogContainer;
import org.fossa.rolp.data.lehrer.lehrerblog.LehrerBlogLaso;
import org.fossa.rolp.util.BlogUtils;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

public class LehrerBlog extends Panel{
	private static final long serialVersionUID = 4356178108589669479L;
	private Label blogtext;
	private RolpApplication app;
	
	public LehrerBlog(RolpApplication app){
		this.app = app;
		
		addStyleName("lehrerBlog");
		
		blogtext = new Label();
		blogtext.setContentMode(Label.CONTENT_XHTML);
		blogtext.setReadOnly(true);
		
		setHeight("250px");
		setScrollable(true);
		
		addComponent(blogtext);
		refreshBlogtext();
	}
	
	public void refreshBlogtext() {
		blogtext.setReadOnly(false);
		blogtext.setValue(getBlogtext());
		blogtext.setReadOnly(true);
	}
	
	private String getBlogtext() {
		String blogCollection = "";
		LehrerPojo lehrer = app.getLoginLehrer();

		for(LehrerBlogLaso blogLaso : LehrerBlogContainer.getInstance().getItemIds()){
			if(blogLaso.getLehrer().getId().equals(lehrer.getId())){
				blogCollection = BlogUtils.createBlogtext(blogLaso) + BlogUtils.LINE_SEPERATOR + blogCollection;
			}
		}
		return blogCollection;
	}
	


}
