package liar.liarChat.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import common.dto.MessageDto;

@ApplicationScoped
public class MessageMap implements MessageInterface,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, MessageDto> pushMap;
	
	@PostConstruct
	public void init() {
		pushMap = new HashMap<>();
	}
	
	@Override
	public void pushMap(MessageDto message) {
		pushMap.put(message.getRoomId()+","+message.getGameId(), message);
	}

	@Override
	public MessageDto getPushMap(String key) {
		return pushMap.get(key);
	}

}
