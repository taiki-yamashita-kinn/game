package common.service.interfaces.google;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


public interface GoogleGmailService {
	//Gmailを送信する
	void sendGmail(Map<String, String> condition) throws AddressException, MessagingException, IOException;

}
