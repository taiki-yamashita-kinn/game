package home.login.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.LoginInfo;
import common.exception.OutLogException;
import home.login.mapper.LoginMapper;
import home.login.service.LoginService;

@RequestScoped
public class LoginServiceImpl implements LoginService{

	@Inject
	private SqlSession sqlSession;

	@Override
	public LoginInfo confirmUser(String userName, String password) {
		LoginInfo result = new LoginInfo();

		try {
			Map<String, Object> condition = new HashMap<>();
			condition.put("userName", userName);
			condition.put("password",  password);

			result = sqlSession.getMapper(LoginMapper.class).confirmLoginUser(condition);

		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return result;
	}
	
	@Override
	public LoginInfo confirmGoogleLogin(String googleId) {
		LoginInfo result = new LoginInfo();
		try {
			result = sqlSession.getMapper(LoginMapper.class).confirmGoogleLoginUser(googleId);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return result;
	}

}
