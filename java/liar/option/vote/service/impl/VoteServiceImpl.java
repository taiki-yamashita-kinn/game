package liar.option.vote.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.LoginInfo;
import common.exception.OutLogException;
import liar.option.vote.dto.VoteDto;
import liar.option.vote.mapper.VoteMapper;
import liar.option.vote.service.VoteService;


@RequestScoped
public class VoteServiceImpl implements VoteService{

	@Inject
	private LoginInfo loginUser;

	@Inject
	private SqlSession sqlSession;

	@Override
	public void vote(String value, String gameId) {
		try {
			//ゲームの添付URLを消去
			sqlSession.getMapper(VoteMapper.class).deleteVote(loginUser.getUserId(),gameId);
			//ゲームの添付URLを挿入
			sqlSession.getMapper(VoteMapper.class).insertVote(loginUser.getUserId(),gameId,value);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());

		}
	}
	
	@Override
	public List<VoteDto> getVote(String gameId) {
		List<VoteDto> vote = new ArrayList<>();
		try {
			//ゲームの添付URLを挿入
			vote = sqlSession.getMapper(VoteMapper.class).selectVote(loginUser.getUserId(),gameId);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return vote;
	}

}
