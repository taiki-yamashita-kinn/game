package home.userEdit.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.exception.OutLogException;
import home.userEdit.dto.UserEditDto;
import home.userEdit.mapper.UserEditMapper;
import home.userEdit.service.UserEditService;

@RequestScoped
public class UserEditServiceImpl implements UserEditService{

	@Inject
	private SqlSession sqlSession;

	@Override
	public UserEditDto selectUser(String userId) {
		UserEditDto result = new UserEditDto ();
		try {
			result = sqlSession.getMapper(UserEditMapper.class).selectUser(userId);
		}catch(Exception e) {
		}
		return result;
	}

	@Override
	public void update(UserEditDto user) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("userId", user.getUserId());
			condition.put("userName", user.getUserName());
			condition.put("lang", user.getUserLanguage());
			condition.put("twitterAccount", user.getTwitterAccount());
			condition.put("theme", user.getTheme());
			condition.put("profile", user.getProfile());
			condition.put("imagePath", user.getPicturePath());
			

			sqlSession.getMapper(UserEditMapper.class).updateUser(condition);
			sqlSession.getMapper(UserEditMapper.class).deleteUserUrl(user.getUserId());
			sqlSession.getMapper(UserEditMapper.class).insertUserUrl(user.getUserId(),user.getUrl());
			sqlSession.commit();
		}catch (Exception e){
			sqlSession.rollback();
			new OutLogException(e.getMessage());
		}
	}


}
