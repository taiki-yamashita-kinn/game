package home.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.UserDto;
import common.exception.OutLogException;
import home.manage.dto.ManageDto;
import home.manage.mapper.ManageMapper;
import home.manage.service.ManageService;

@RequestScoped
public class ManageServiceImpl implements ManageService{

	@Inject
	private SqlSession sqlSession;

	@Override
	public List<UserDto> searchUser(ManageDto user) {
		List<UserDto> results = new ArrayList<>();
		try {
			Map<String, Object> condition = new HashMap<>();
			condition.put("userName", user.getUserName());
			condition.put("twitterAccount", user.getTwitterAccount());
			results = sqlSession.getMapper(ManageMapper.class).searchUser(condition);

		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}

}
