package liar.liarChat.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.ChatRoomDto;
import common.dto.LoginInfo;
import common.dto.MessageDto;
import common.exception.OutLogException;
import liar.liarChat.mapper.LiarChatMapper;
import liar.liarChat.service.LiarChatService;

@RequestScoped
public class LiarChatServiceImpl implements LiarChatService{

	@Inject
	private SqlSession sqlSession;

	@Inject
	private LoginInfo loginInfo;

	//ゲームが発言禁止かどうかを取得する
	@Override
	public String selectRoomName(ChatRoomDto chatRoom) {
		String result = null;
		Map<String, Object> condition = new HashMap<>();
		condition.put("userId", loginInfo.getUserId());
		condition.put("roomId", chatRoom.getRoomId());
		condition.put("gameId", chatRoom.getGameId());
		try {
			result = sqlSession.getMapper(LiarChatMapper.class).selectRoomName(condition);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return result;
	}

	//メッセージのリストを取得する
	@Override
	public List<MessageDto> selectMessage(ChatRoomDto chatRoom) {
		List<MessageDto> results = new ArrayList<>();
		Map<String, Object> condition = new HashMap<>();
		condition.put("userId", loginInfo.getUserId());
		condition.put("roomId", chatRoom.getRoomId());
		condition.put("gameId", chatRoom.getGameId());
		try {
			results = sqlSession.getMapper(LiarChatMapper.class).selectGameMessage(condition);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}

	//メッセージを登録する
	@Override
	public void insertMessage(MessageDto message) {
		Map<String, Object> condition = new HashMap<>();
		condition.put("userId", message.getUserId());
		condition.put("roomId", message.getRoomId());
		condition.put("gameId", message.getGameId());
		condition.put("message", message.getMessage());
		try {
			sqlSession.getMapper(LiarChatMapper.class).insertGameMessage(condition);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());
		}
	}

	//ゲームが発言禁止かどうかを取得する
	@Override
	public ChatRoomDto selectGame(String gameId) {
		ChatRoomDto result = new ChatRoomDto();
		try {
			result = sqlSession.getMapper(LiarChatMapper.class).selectGameSpeakFlag(gameId);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return result;
	}
	
}
