package liar.newBuildLiarGame.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.bean.LangLabelBean;
import common.dto.LiarGameDto;
import common.dto.LoginInfo;
import common.exception.OutLogException;
import common.mapper.CommonMapper;
import common.service.interfaces.google.GoogleCalenderService;
import common.service.interfaces.timer.GameScheduleService;
import common.service.interfaces.twitter.TwitterPostService;
import liar.newBuildLiarGame.mapper.NewBuildLiarGameMapper;
import liar.newBuildLiarGame.service.NewBuildLiarGameService;

@RequestScoped
public class NewBuildLiarGameServiceImpl implements NewBuildLiarGameService {

	@Inject
	private SqlSession sqlSession;

	@Inject
	private GameScheduleService gameScheduleService;

	private String startTime = null;

	private String endTime = null;

	@Inject
	private LoginInfo loginInfo;

	@Inject
	private TwitterPostService twitterPostService;

	@Inject
	private GoogleCalenderService googleCalenderService;

	@Inject
	private LangLabelBean langLabelBean;

	//ゲーム説明URL
	private String descriptionUrl = "/faces/liar/explainLiarGame.xhtml?gameId=";

	//プロパティファイル読み込み
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

	@Override
	public void insertGame(LiarGameDto newLiarGame) {
		try {
			//日付変換
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        if(newLiarGame.getStartTime() != null) {
	            startTime = sdf.format(newLiarGame.getStartTime());
	        }
	        if(newLiarGame.getEndTime() != null) {
	        	endTime = sdf.format(newLiarGame.getEndTime());
	        }
	
	        //ログインユーザーのIDを作成者のIDにセット
	        newLiarGame.setUserMasterId(loginInfo.getUserId());
	
			Map<String, Object> condition = new HashMap<String, Object>();
	
			condition.put("gameName", newLiarGame.getGameName());
			condition.put("userMasterId", newLiarGame.getUserMasterId());
			condition.put("startTime", startTime);
			condition.put("endTime", endTime);
			condition.put("peopleCount", newLiarGame.getPeopleCount());
			condition.put("separateTime", newLiarGame.getSeparateTime());
			condition.put("oneDaySessionTimes", newLiarGame.getOneDaySessionTimes());
			condition.put("banMinutes", newLiarGame.getBanSpeakMinutes());
			condition.put("evaluation", newLiarGame.getEvaluation());
			condition.put("level", newLiarGame.getLevel());
			condition.put("explanation", newLiarGame.getExplanation());
			condition.put("documentId", newLiarGame.getDocumentUrl());
			condition.put("presentationId", newLiarGame.getPresentationUrl());
			condition.put("spreadId", newLiarGame.getSpreadUrl());
			condition.put("gameOpen", newLiarGame.getGameOpen());
			condition.put("accessCode", newLiarGame.getAccessCode());
			condition.put("isStop", "0");
	
			try {
				//新しいゲームを挿入する
				sqlSession.getMapper(NewBuildLiarGameMapper.class).insertLiarGame(condition);
				//ゲームの参加ユーザー一覧にユーザー挿入
				sqlSession.getMapper(CommonMapper.class).insertJoin(loginInfo.getUserId(), (String) condition.get("id"));
				//ユーザーをゲーム参加状態にする
				sqlSession.getMapper(NewBuildLiarGameMapper.class).updateUser(loginInfo.getUserId(), (String) condition.get("id"));
				//ゲームの添付URLを消去
				//sqlSession.getMapper(NewBuildLiarGameMapper.class).deleteGameUrl((String) condition.get("id"));
				//ゲームの添付URLを挿入
				sqlSession.getMapper(NewBuildLiarGameMapper.class).insertGameUrl(condition);
				sqlSession.commit();
			}catch(Exception e) {
				sqlSession.rollback();
				new OutLogException(e.getMessage());
	
			}
	
			try {
				//ゲームの作成を通知する
				//twitterPostService.postTwitter(resourceBundle.getString("tweet_tag")+System.getProperty("line.separator")
					//	+resourceBundle.getString("hostUrl")+"faces/liar/explainLiarGame.xhtml?gameId="+(String) condition.get("id"));
	
			} catch (Exception e) {
				new OutLogException(e.getMessage());
			}
	
			//文字列ビルダー
			StringBuilder builder = new StringBuilder();
	
			builder.append(langLabelBean.getLabel("attach_url_game")+" ");
			if(newLiarGame.getUrl().isEmpty()) {
				builder.append(langLabelBean.getLabel("nothing"));
			}else {
				builder.append(newLiarGame.getUrl());
			}
	
			builder.append(langLabelBean.getLabel("link_on_game")+" ");
			builder.append("https://www.onlineliar.com");
			builder.append(descriptionUrl+(String) condition.get("id"));
	
			//カレンダー説明文をセット
			newLiarGame.setDescription(builder.toString());
	
			//Googleカレンダーにゲームを登録する
			//googleCalenderService.registerGameCalendar(newLiarGame);
	
			//作成ユーザーにゲームIDをセットする
			loginInfo.setGameId((String) condition.get("id"));
	
			//挿入したidを取得し、オブジェクトに格納
			newLiarGame.setId((String) condition.get("id"));
	
			//システム用のメッセージをinsertする
			condition.put("sysMessage", "ゲーム開始時間:"+startTime
					+System.getProperty("line.separator")+"ゲーム開始時までチャットグループの作成、発言はできません。少々お待ちください");
			
				sqlSession.getMapper(NewBuildLiarGameMapper.class).insertSystemMessage(condition);
				sqlSession.commit();
		}catch(Exception e) {
			sqlSession.rollback();
			new OutLogException(e.getMessage());
		}
		try {
			//ゲーム開始をスケジューリングする
			gameScheduleService.gameStart(newLiarGame);

		} catch (Exception e) {
			new OutLogException(e.getMessage());
		}

	}


}
