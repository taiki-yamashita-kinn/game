package liar.explainLiarGame.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import common.bean.LangLabelBean;
import common.dto.LiarGameDto;
import common.dto.LoginInfo;
import common.exception.OutLogException;
import common.service.interfaces.common.CommonService;
import common.util.chat.ChatServerUpdatePageEndpoint;
import liar.explainLiarGame.service.ExplainLiarGameService;
import liar.liarGamePage.bean.ViewGameIdBean;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class ExplainLiarGameBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//説明画面の画面
	@Getter
	@Setter
	private LiarGameDto game;

	//画面で入力されたアクセスコード
	@Getter
	@Setter
	private String accessCode;

	@Inject
	private ExplainLiarGameService explainLiarGameService;

	@Inject
	private LoginInfo loginInfo;

	//ボタンの名前をフラグで変える
	@Getter
	@Setter
	private String buttonTitle = StringUtils.EMPTY;

	//宣伝用TwitterURL
	@Getter
	@Setter
	private String advertiseUrl = StringUtils.EMPTY;

	//参加済みかどうか、参加できたかどうかのフラグ
	@Getter
	@Setter
	private boolean joinFlag = false;

	//参加人数がいっぱいかどうかのフラグ
	@Getter
	@Setter
	private boolean buttonFlag = false;

	@Inject
	private LangLabelBean langLabelBean;

	@Getter
	@Setter
	private boolean timeFlag = true;
	
	//ゲームIDをViewDtoで持っている
	@Inject
	private ViewGameIdBean viewGameIdBean;
	
	@Inject
	private CommonService commonService;
	
	//プロパティファイル読み込み
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

	@PostConstruct
	public void init() {

		try {
			game = new LiarGameDto();
			
			//URLパラメータがないなら処理終了
			if(!FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("gameId")) {
				buttonFlag = true;
				return;
			}
			//ユーザがゲームに参加済みか取得する
			joinFlag = !commonService.selectJoinUser(viewGameIdBean.getGameId()).equals("0");
			//パラメータでゲーム検索
			game = explainLiarGameService.selectLiarGame(viewGameIdBean.getGameId());
			game.setSeparateTime(game.getSeparateTime());
			//ログインユーザーが参加済みなら
			if(joinFlag) {
				buttonTitle =langLabelBean.getLabel("back_to_game");
			} else {
				buttonTitle =langLabelBean.getLabel("join");
			}
			//参加人数がいっぱいなら参加ボタンをdisableにする
			if(game.getPeopleCount()!= 0 && game.getPeopleCount()<=game.getNowPeopleCount()) {
				buttonFlag = true;
			}

			//参加人数いっぱいのエラーメッセージ
			if(buttonFlag && !joinFlag) {
				FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error4"), null));
			}

			// 変換対象の日付文字列
	        String startDateStr = game.getStartTimeData();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        // Date型変換
	        Date startFormatDate = sdf.parse(startDateStr);
	        game.setStartTime(startFormatDate);
			//ゲーム開始時刻が現在の日付より後なら非表示にする
			if(game.getStartTime().compareTo(new Date()) != 1) {
				timeFlag = false;
			}
			/**
			 * ゲームの時間を時に変換
			 */
			/*if(game.getSeparateTime()>60) {
				game.setSeparateTime(game.getSeparateTime()/60);
			}*/
			//宣伝URLの作成
			advertiseUrl = "https://twitter.com/intent/tweet?text="
					+ resourceBundle.getString("tweet_tag")+System.getProperty("line.separator")
					+ resourceBundle.getString("hostUrl")+"faces/liar/explainLiarGame.xhtml?gameId="+game.getId();
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}

	}

	public void transJoin(String gameId) {

		//ログインしていない
		if(StringUtils.isEmpty(loginInfo.getUserId())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error1"), null));
			return;
		}
		
		try {
			//ゲームに参加済みならページ移動のみ
			if(joinFlag) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("liarGamePage.xhtml?gameId="+gameId);
				return;
			}
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}

		//----------------------------ゲームに参加済みじゃない-----------------------------

		//参加できたかどうかのフラグ
		boolean successFlag = false;

		//二つ以上のゲームには参加できない
		if(loginInfo.getIsGame().equals("1")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error2"), null));
			return;
		}

		//アクセスコード不正
		//画面入力のアクセスコードと登録されているゲームのアクセスコードが等しくない
		if(!StringUtils.isEmpty(game.getAccessCode()) && !game.getAccessCode().equals(accessCode)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error4"), null));
			return;
		}

		//ゲームに参加していない場合処理をする
		if(!loginInfo.getGameId().matches(gameId)) {
			//成功したらtrue
			successFlag = explainLiarGameService.joinGame(loginInfo.getUserId(), game.getId());
		}

		//ゲーム参加人数がいっぱいだった場合
		if(!successFlag) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error4"), null));
			return;
		}
		//ゲームのIDをユーザーにセットする
		loginInfo.setGameId(gameId);
		//ログインユーザーの設定変更
		loginInfo.setIsGame("1");

		try {
			//ゲームに参加できたまたは、ゲームに参加している
			if(loginInfo.getGameId().matches(gameId)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("liarGamePage.xhtml?gameId="+gameId);
				ChatServerUpdatePageEndpoint.broadCast("push",gameId);
			}
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
	}

	public void viewGame(String gameId) {
		try {
			//ログインしていない
			if(StringUtils.isEmpty(loginInfo.getUserId())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error1"), null));
				return;
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("liarGamePage.xhtml?gameId="+gameId);
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
	}

}
