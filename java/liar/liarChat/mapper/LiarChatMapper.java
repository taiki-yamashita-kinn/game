package liar.liarChat.mapper;

import java.util.List;
import java.util.Map;

import common.dto.ChatRoomDto;
import common.dto.MessageDto;

public interface LiarChatMapper {

	String selectRoomName(Map<String, Object> condition);

	List<MessageDto> selectGameMessage(Map<String, Object> condition);

	void insertGameMessage(Map<String, Object> condition);

	ChatRoomDto selectGameSpeakFlag(String gameID);

}
