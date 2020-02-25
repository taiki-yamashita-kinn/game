package liar.gameMasterManage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.exception.OutLogException;
import liar.gameMasterManage.dto.GameMasterManageDto;
import liar.gameMasterManage.dto.VoteDto;
import liar.gameMasterManage.mapper.GameMasterManageMapper;
import liar.gameMasterManage.service.GameMasterManageService;

@RequestScoped
public class GameMasterManageServiceImpl implements GameMasterManageService{

	@Inject
	private SqlSession sqlSession;

	@Override
	public List<SelectItem> selectUserList(String gameId) {
		List<SelectItem> results = new ArrayList<>();
		try {
			results = sqlSession.getMapper(GameMasterManageMapper.class).selectUserList(gameId);
		} catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}

	@Override
	public List<GameMasterManageDto> selectGameMessage(GameMasterManageDto searchData) {
		List<GameMasterManageDto> results = new ArrayList<>();
		Map<String, Object> condition = new HashMap<>();
		condition.put("userId", searchData.getUserId());
		condition.put("roomId", searchData.getRoomId());
		condition.put("gameId", searchData.getGameId());
		condition.put("message", searchData.getMessage());
		condition.put("round", searchData.getRound());
		try {
			results = sqlSession.getMapper(GameMasterManageMapper.class).selectMessageList(condition);
		} catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}

	@Override
	public List<VoteDto> selectVoteData(VoteDto voteData) {
		List<VoteDto> results = new ArrayList<>();
		Map<String, Object> condition = new HashMap<>();
		condition.put("userId", voteData.getUserId());
		condition.put("gameId", voteData.getGameId());
		condition.put("message", voteData.getMessage());
		condition.put("startDate", voteData.getStartDate());
		condition.put("endDate", voteData.getEndDate());
		condition.put("round", voteData.getRound());
		try {
			results = sqlSession.getMapper(GameMasterManageMapper.class).selectVoteList(condition);
		} catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}

}
