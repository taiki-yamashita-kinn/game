package liar.option.question.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.LoginInfo;
import common.exception.OutLogException;
import liar.option.question.dto.QuestionDto;
import liar.option.question.mapper.QuestionMapper;
import liar.option.question.service.QuestionService;


@RequestScoped
public class QuestionServiceImpl implements QuestionService{

	@Inject
	private LoginInfo loginUser;

	@Inject
	private SqlSession sqlSession;

	@Override
	public void question(String value, String gameId) {
		try {
			//ゲームの添付URLを挿入
			sqlSession.getMapper(QuestionMapper.class).insertQuestion(loginUser.getUserId(),gameId,value);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());

		}
	}
	
	@Override
	public List<QuestionDto> getQuestion(String userId, String gameId) {
		List<QuestionDto> question = new ArrayList<>();
		try {
			//ゲームの添付URLを挿入
			question = sqlSession.getMapper(QuestionMapper.class).selectQuestion(userId,gameId);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return question;
	}
	
	@Override
	public void updateQuestion(String id, String yesNo) {
		try {
			//質問のYES NOを更新する
			sqlSession.getMapper(QuestionMapper.class).updateQuestion(id, yesNo);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());
		}
	}

}
