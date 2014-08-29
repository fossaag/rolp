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

package org.fossa.vaadin.laso;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.fossa.rolp.util.HibernateUtil;
import org.fossa.vaadin.data.FossaPojo;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class FossaLaso implements Serializable {
	
	private static final long serialVersionUID = -2529423300512854145L;
	
	protected Boolean locked = false;
	
	public abstract Long getId();

	public abstract void setId(Long id);
	
	public abstract Object getPojo();

	public Boolean isLocked() {
		return locked;
	}

	public void lock() {
		this.locked = true;
	}
	
	public void unlock() {
		this.locked = false;
	}

	protected abstract void writeToDatabase();
	
	protected void writeToDatabase(Object object) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(object);
		transaction.commit();
	}
	
	public static Collection<?> getAll(Class<?> pojoClass) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<?> objectList = session.createQuery("from "+pojoClass.getSimpleName()).list();
		transaction.commit();
		return objectList;
	}
	
	public static void deleteIfExists(Object object) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.delete(object);
		transaction.commit();
	}
}
