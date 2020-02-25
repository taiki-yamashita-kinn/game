package liar.editLiarGame.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.LiarGameDto;
import common.dto.LoginInfo;
import common.exception.OutLogException;
import common.service.interfaces.timer.GameScheduleService;
import liar.editLiarGame.mapper.EditLiarGameMapper;
import liar.editLiarGame.service.EditLiarGameService;

@RequestScoped
public class EditLiarGameServiceImpl implements EditLiarGameService{

	@Inject
	private SqlSession sqlSession;

	private String startTime = null;

	private String endTime = null;

	@Inject
	private LoginInfo loginInfo;

	@Inject
	private GameScheduleService gameScheduleService;

	@Override
	public void updateGame(LiarGameDto updateLiarGame)  {

		try {
		//日付変換
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if(updateLiarGame.getStartTime() != null) {
            startTime = sdf.format(updateLiarGame.getStartTime());
        }
        if(updateLiarGame.getEndTime() != null) {
        	endTime = sdf.format(updateLiarGame.getEndTime());
        }

        //ログインユーザーのIDを作成者のIDにセット
        updateLiarGame.setUserMasterId(loginInfo.getUserId());

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("gameId", updateLiarGame.getId());
		condition.put("gameName", updateLiarGame.getGameName());
		condition.put("startTime", startTime);
		condition.put("endTime", endTime);
		condition.put("peopleCount", updateLiarGame.getPeopleCount());
		condition.put("separateTime", updateLiarGame.getSeparateTime());
		condition.put("oneDaySessionTimes", updateLiarGame.getOneDaySessionTimes());
		condition.put("documentId", updateLiarGame.getDocumentUrl());
		condition.put("presentationId", updateLiarGame.getPresentationUrl());
		condition.put("spreadId", updateLiarGame.getSpreadUrl());
		condition.put("banMinutes", updateLiarGame.getBanSpeakMinutes());
		condition.put("evaluation", updateLiarGame.getEvaluation());
		condition.put("level", updateLiarGame.getLevel());
		condition.put("explanation", updateLiarGame.getExplanation());
		condition.put("gameOpen", updateLiarGame.getGameOpen());

		try {
			//ゲームを更新する
			sqlSession.getMapper(EditLiarGameMapper.class).updateLiarGame(condition);
			//ゲームの添付URLを更新
			sqlSession.getMapper(EditLiarGameMapper.class).updateGameUrl(condition);
			sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());

		}
			//ゲーム更新をスケジューリングする
			gameScheduleService.gameStart(updateLiarGame);

		} catch (Exception e) {
			new OutLogException(e.getMessage());
		}
	}

	@Override
	public LiarGameDto selectLiarGame(String gameId) {
		LiarGameDto result = new LiarGameDto();
		try {
			result = sqlSession.getMapper(EditLiarGameMapper.class).selectLiarGame(gameId);

		}catch(Exception e){
			new OutLogException(e.getMessage());
		}
		return result;
	}

}
