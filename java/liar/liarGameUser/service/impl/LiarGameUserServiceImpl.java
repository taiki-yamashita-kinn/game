package liar.liarGameUser.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.MessageDto;
import common.dto.UserDto;
import common.exception.OutLogException;
import liar.liarGameUser.mapper.LiarGameUserMapper;
import liar.liarGameUser.service.LiarGameUserService;

@RequestScoped
public class LiarGameUserServiceImpl implements LiarGameUserService{

	@Inject
	private SqlSession sqlSession;

	@Override
	public UserDto selectUser(String userId) {
		UserDto result = new UserDto();
		try {
			result = sqlSession.getMapper(LiarGameUserMapper.class).selectUser(userId);
		}catch(Exception e) {
			new OutLogException(e.getMessage());

		}
		return result;
	}

	@Override
	public List<MessageDto> selectMessages(String gameId, String userId) {
		List<MessageDto> results = new ArrayList<>();
		try {
			results = sqlSession.getMapper(LiarGameUserMapper.class).selectMessages(gameId, userId);
		}catch(Exception e) {
			new OutLogException(e.getMessage());

		}
		return results;
	}

}
