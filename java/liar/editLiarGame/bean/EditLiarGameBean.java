package liar.editLiarGame.bean;

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

import org.primefaces.PrimeFaces;

import common.dto.LiarGameDto;
import common.dto.LoginInfo;
import common.exception.OutLogException;
import liar.editLiarGame.service.EditLiarGameService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class EditLiarGameBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//説明画面の画面
	@Getter
	@Setter
	private LiarGameDto game;

	@Inject
	private EditLiarGameService editLiarGameService;

	//ボタンの名前をフラグで変える
	@Getter
	@Setter
	private String buttonTitle = null;

	@Inject
	private LoginInfo loginInfo;

	@Getter
	@Setter
	private String param = null;

	@Getter
	@Setter
	private boolean timeFlag = false;
	
	//プロパティファイル読み込み
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

	@PostConstruct
	public void init() {

		try {
			game = new LiarGameDto();

			//編集用のゲームを取得
			game = editLiarGameService.selectLiarGame(loginInfo.getGameId());

			// 変換対象の日付文字列
	        String startDateStr = game.getStartTimeData();

	        // 変換対象の日付文字列
	        String endDateStr = game.getEndTimeData();

	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	        // Date型変換
	        Date startFormatDate = sdf.parse(startDateStr);

	        // Date型変換
	        Date endFormatDate = sdf.parse(endDateStr);

	        game.setStartTime(startFormatDate);

	        game.setEndTime(endFormatDate);

			//ゲーム開始時刻が現在の日付より後、ユーザーマスターじゃないなら非活性にする
			if(game.getStartTime().compareTo(new Date()) != 1 && !loginInfo.getUserId().equals(game.getUserMasterId())) {
				timeFlag = true;
			}

		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}

	}

	/**
	 * ゲームを更新する
	 */
	public void updateGame() {
		//ゲーム開始時刻が適切かどうか
		if(game.getStartTime().compareTo(new Date()) != 1) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error7"), null));
			return;
		}

		//ゲーム終了時刻が適切かどうか
		if(game.getEndTime().compareTo(new Date()) != 1) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error8"), null));
			return;
		}

		//ゲーム開始、終了時刻が適切な範囲かどうか
		if(game.getStartTime().compareTo(game.getEndTime()) != -1) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error9"), null));
			return;
		}
		//発言禁止時間1が適切な時間かどうか
		if(game.getBanSpeakTime1() != null) {
			if(game.getBanSpeakTime1().compareTo(game.getStartTime()) != 1
				|| game.getBanSpeakTime1().compareTo(game.getEndTime()) != -1) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error10"), null));
				return;

			}

		}
		try {
			editLiarGameService.updateGame(game);
		} catch (Exception e) {
			new OutLogException(e.getMessage());
		}
		PrimeFaces.current().dialog().closeDynamic("");

	}

}
