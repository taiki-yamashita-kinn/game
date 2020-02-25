package liar.liarUserList.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.LoginInfo;
import common.exception.OutLogException;
import liar.liarUserList.dto.LiarUserListDto;
import liar.liarUserList.mapper.LiarUserListMapper;
import liar.liarUserList.service.LiarUserListService;

@RequestScoped
public class LiarUserListServiceImpl implements LiarUserListService{

	@Inject
	private SqlSession sqlSession;

	@Inject
	private LoginInfo loginUser;


	@Override
	public List<LiarUserListDto> selectJoinUser(String gameId) {
		List<LiarUserListDto> results = new ArrayList<>();
		try {
			results = sqlSession.getMapper(LiarUserListMapper.class).selectJoinUser(gameId,loginUser.getUserId());
			return results;
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}

	@Override
	public boolean registerChattRoom(List<LiarUserListDto> chatRoomList, String gameId, String roomName) {

		//String registerRoomId = null;
		//登録する部屋IDを取得する
		/*
		 * synchronized (sqlSession) { try { registerRoomId =
		 * sqlSession.getMapper(LiarUserListMapper.class).selectRoomId(gameId); }catch
		 * (Exception e){ sqlSession.rollback(); new OutLogException(e.getMessage()); }
		 * }
		 */

		Map<String, Object> condition = new HashMap<>();

		condition.put("gameId", gameId);
		condition.put("roomName", roomName);
		try {
			sqlSession.getMapper(LiarUserListMapper.class).insertChatRoomManage(condition);

			// 選択したチャットルームに登録するユーザーの数だけループを回す
			for (LiarUserListDto data : chatRoomList) {
				condition.put("userId", data.getUserId());
				try {
					sqlSession.getMapper(LiarUserListMapper.class).insertChatRoom(condition);
				} catch (Exception e) {
					sqlSession.rollback();
					new OutLogException(e.getMessage());
				}
			}
			
			//ログインユーザのチャットルームを追加する
			condition.put("userId", loginUser.getUserId());
			sqlSession.getMapper(LiarUserListMapper.class).insertChatRoom(condition);
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());
		}
		
		return true;

	}
}
