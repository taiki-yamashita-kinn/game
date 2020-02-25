package home.userSearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.UserDto;
import common.exception.OutLogException;
import home.userSearch.dto.UserSearchDto;
import home.userSearch.mapper.UserSearchMapper;
import home.userSearch.service.UserSearchService;

@RequestScoped
public class UserSearchServiceImpl implements UserSearchService {

	@Inject
	private SqlSession sqlSession;

	@Override
	public List<UserDto> searchUser(UserSearchDto user) {
		List<UserDto> results = new ArrayList<>();
		try {
			Map<String, Object> condition = new HashMap<>();
			condition.put("userName", user.getUserName());
			condition.put("twitterAccount", user.getTwitterAccount());
			condition.put("evaluationFrom", user.getEvaluationFrom());
			condition.put("battleCountFrom", user.getBattleCountFrom());
			condition.put("evaluationTo", user.getEvaluationTo());
			condition.put("battleCountTo", user.getBattleCountTo());
			results = sqlSession.getMapper(UserSearchMapper.class).searchUser(condition);

		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}

}
