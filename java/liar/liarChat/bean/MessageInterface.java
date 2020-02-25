package liar.liarChat.bean;

import common.dto.MessageDto;

public interface MessageInterface {
	void pushMap(MessageDto message);
	
	MessageDto getPushMap(String key);

}
