package common.service.impl.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.LoginInfo;
import common.dto.UserDto;
import common.exception.OutLogException;
import common.mapper.CommonMapper;
import common.service.interfaces.common.CommonService;

@RequestScoped
public class CommonServiceImpl implements CommonService {

	@Inject
	private SqlSession sqlSession;

	@Inject
	private LoginInfo loginInfo;
	
	@Override
	public UserDto selectUser(String userId) {
		UserDto result = new UserDto ();
		try {
			result = sqlSession.getMapper(CommonMapper.class).selectUser(userId);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return result;
	}

	@Override
	public LoginInfo selectLoginUser(String userId) {
		LoginInfo result = new LoginInfo ();
		try {
			result = sqlSession.getMapper(CommonMapper.class).selectLoginUser(userId);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return result;
	}

	@Override
	public void deleteUser(String userId) {
		try {
			sqlSession.getMapper(CommonMapper.class).deleteUser(userId);
			sqlSession.getMapper(CommonMapper.class).deleteUserMessage(userId);
			sqlSession.commit();
		}catch(Exception e) {
			new OutLogException(e.getMessage());
			sqlSession.rollback();
		}

	}

	@Override
	public void stopUser(String userId) {
		try {
			sqlSession.getMapper(CommonMapper.class).stopUser(userId);
			sqlSession.commit();
		}catch(Exception e) {
			new OutLogException(e.getMessage());
			sqlSession.rollback();
		}

	}

	@Override
	public void deleteGameChatMessage(String gameId) {
		try {
			sqlSession.getMapper(CommonMapper.class).deleteGame(gameId);
			sqlSession.getMapper(CommonMapper.class).deleteChat(gameId);
			sqlSession.getMapper(CommonMapper.class).deleteGameMessage(gameId);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());
		}

	}

	//ゲームの発言禁止を解除したり禁止したりする
	@Override
	public void updateSpeakFlag(String flag, String gameId) {
		try {
			sqlSession.getMapper(CommonMapper.class).updateSpeakFlag(flag, gameId);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());
		}
	}

	//ユーザーの評価を上げる
	@Override
	public void updateGameEndUser(String gameId) {
		try {
			sqlSession.getMapper(CommonMapper.class).updateGameEndUser(gameId);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());
		}

	}

	/**
	 * 多言語のラベル一覧を取得
	 */
	@Override
	public List<Map<String, Object>> selectLabel() {

		List<Map<String, Object>> results = new ArrayList<>();
		try {
			results = sqlSession.getMapper(CommonMapper.class).selectLabel();
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}
	
	//メッセージのリストを取得する
	@Override
	public String selectJoinUser(String gameId) {
		String count = null;
		try {
			count = sqlSession.getMapper(CommonMapper.class).selectJoinUser(loginInfo.getUserId(),gameId);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return count;
	}



}
