package liar.liarGamePage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.LiarGameDto;
import common.dto.LoginInfo;
import common.exception.OutLogException;
import liar.liarGamePage.dto.LiarGamePageDto;
import liar.liarGamePage.mapper.LiarGamePageMapper;
import liar.liarGamePage.service.LiarGamePageService;
import liar.liarUserList.dto.LiarUserListDto;

@RequestScoped
public class LiarGamePageServiceImpl implements LiarGamePageService{

	@Inject
	private SqlSession sqlSession;

	@Inject
	private LoginInfo loginInfo;

	@Override
	public LiarGameDto selectGame(String gameId) {
		LiarGameDto result = new LiarGameDto();
		try {
			result = sqlSession.getMapper(LiarGamePageMapper.class).selectGame(gameId,loginInfo.getUserId());
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return result;
	}

	@Override
	public List<LiarUserListDto> selectUserList(String gameId) {
		List<LiarUserListDto> results = new ArrayList<>();
		try {
			results = sqlSession.getMapper(LiarGamePageMapper.class).selectUserList(gameId);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}

	@Override
	public List<LiarGamePageDto> selectChatUser(String gameId,String userId) {
		List<LiarGamePageDto> results = new ArrayList<>();
		try {
			results = sqlSession.getMapper(LiarGamePageMapper.class).selectChatUser(gameId,userId);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}

	@Override
	public void insertStatus(String userId, String gameId, String status) {
		try {
			//コマンドが押されたことをDBに挿入する
			sqlSession.getMapper(LiarGamePageMapper.class).insertStatus(userId,gameId,status);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());
		}
	}

	@Override
	public void leaveGame(String userId, String gameId) {
		try {
			//ゲームを退出させる
			sqlSession.getMapper(LiarGamePageMapper.class).leaveGame(userId);
			sqlSession.getMapper(LiarGamePageMapper.class).decreaseJoin(gameId);
			sqlSession.getMapper(LiarGamePageMapper.class).deleteJoinGame(userId,gameId);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());
		}
	}

}
