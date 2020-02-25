package liar.option.ruleFlagChange.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.LoginInfo;
import common.exception.OutLogException;
import liar.option.ruleFlagChange.mapper.RuleFlagChangeMapper;
import liar.option.ruleFlagChange.service.RuleFlagChangeService;


@RequestScoped
public class RuleFlagChangeServiceImpl implements RuleFlagChangeService{

	@Inject
	private LoginInfo loginUser;

	@Inject
	private SqlSession sqlSession;

	@Override
	public void flagChange(String value, String gameId) {

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("gameId", gameId);
		condition.put("value", value);
		try {
			//ゲームの表示フラグを更新できるか検索する
			//Map<String, Object> results = sqlSession.getMapper(RuleFlagChangeMapper.class).selectFlagChange(condition);
			//ゲームの表示フラグを変更する
			sqlSession.getMapper(RuleFlagChangeMapper.class).updateUserFlagChange(condition);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());

		}
	}

	@Override
	public void insertChange(String value, String message, String gameId) {

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userId", loginUser.getUserId());
		condition.put("gameId", gameId);
		condition.put("value", value);
		condition.put("successMessage", message);
		try {
			//ゲームの表示フラグを決定する
			sqlSession.getMapper(RuleFlagChangeMapper.class).insertFlagChange(condition);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());

		}
	}

}
