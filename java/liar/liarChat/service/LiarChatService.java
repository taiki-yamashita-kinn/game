package liar.liarChat.service;

import java.util.List;

import common.dto.ChatRoomDto;
import common.dto.MessageDto;

public interface LiarChatService {

	String selectRoomName(ChatRoomDto chatRoom);

	List<MessageDto> selectMessage(ChatRoomDto chatRoom);

	void insertMessage(MessageDto message);

	ChatRoomDto selectGame(String gameId);
	
}
