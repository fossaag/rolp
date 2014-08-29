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

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailUtil {
	public static void sendmail(String mailadresse, String mailtext ) 
	{
try {
        String host="mail.gmx.net";
        int port=587;
        String user="rolp.1@gmx.de";
        String pass="Passwort";
        
        Properties props=new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Session session=Session.getInstance(props);
        Transport transport = null;
		transport = session.getTransport("smtp");
		transport.connect(host, port, user, pass);
        Address[] addresses = null;
        addresses = InternetAddress.parse(mailadresse);
        Message message=new MimeMessage(session);
		message.setFrom(new InternetAddress(user));
		message.setRecipients(Message.RecipientType.TO, addresses); 
		message.setSubject("A Testmail");  
		message.setText(mailtext);  
		transport.sendMessage(message, addresses);	
		transport.close();
} catch (Exception e) {

	
}
	}
}

