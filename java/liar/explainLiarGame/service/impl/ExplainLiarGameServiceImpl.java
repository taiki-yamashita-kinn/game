package liar.explainLiarGame.service.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.LiarGameDto;
import common.exception.OutLogException;
import common.mapper.CommonMapper;
import liar.explainLiarGame.mapper.ExplainLiarGameMapper;
import liar.explainLiarGame.service.ExplainLiarGameService;

@RequestScoped
public class ExplainLiarGameServiceImpl implements ExplainLiarGameService{

	@Inject
	private SqlSession sqlSession;

	@Override
	public LiarGameDto selectLiarGame(String gameId) {
		LiarGameDto result = new LiarGameDto();
		try {
			result = sqlSession.getMapper(ExplainLiarGameMapper.class).selectLiarGame(gameId);

		}catch(Exception e){
			new OutLogException(e.getMessage());
		}
		return result;
	}

	@Override
	public boolean joinGame(String userId, String gameId) {
		LiarGameDto result = new LiarGameDto();
		try {
			result = sqlSession.getMapper(ExplainLiarGameMapper.class).selectLiarGame(gameId);
			if(result.getNowPeopleCount()<result.getPeopleCount()) {
				//ゲーム参加
				sqlSession.getMapper(ExplainLiarGameMapper.class).joinGame(userId, gameId);
				//ゲーム参加人数プラス
				sqlSession.getMapper(ExplainLiarGameMapper.class).joinUserPlus(gameId);
				//ゲームの参加ユーザー一覧にユーザー挿入
				sqlSession.getMapper(CommonMapper.class).insertJoin(userId, gameId);
				sqlSession.commit();
			}else {
				return false;
			}
		}catch(Exception e){
			sqlSession.rollback();
			new OutLogException(e.getMessage());
		}
		return true;
	}

}
