package liar.newBuildLiarGame.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import common.bean.LangLabelBean;
import common.dto.LiarGameDto;
import common.dto.LoginInfo;
import common.exception.OutLogException;
import liar.newBuildLiarGame.service.NewBuildLiarGameService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class NewBuildLiarGameBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private LiarGameDto newGame;

	@Inject
	private LoginInfo loginUser;

	@Inject
	private NewBuildLiarGameService newBuildLiarGameService;

	//ボタンの名前
	@Getter
	@Setter
	private String buttonName;

	//Twitterでゲームを宣伝するかどうかのフラグ
	@Getter
	@Setter
	private boolean advertiseTwitterFlag;

	@Inject
	private LangLabelBean langLabelBean;

	//プロパティファイル読み込み
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");


	@PostConstruct
	public void init() {
		newGame = new LiarGameDto();
	}

	public void register(){
		try {
			//ゲーム終了時刻が適切かどうか
			if(newGame.getStartTime().compareTo(new Date()) != 1) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error7"), null));
				return;
			}
	
			//ゲーム開始時刻が適切かどうか
			if(newGame.getEndTime().compareTo(new Date()) != 1) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error8"), null));
				return;
			}
	
			//ゲーム開始、終了時刻が適切な範囲かどうか
			if(newGame.getStartTime().compareTo(newGame.getEndTime()) != -1) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error9"), null));
				return;
			}
			//発言禁止時間1が適切な時間かどうか
			if(newGame.getBanSpeakTime1() != null) {
				if(newGame.getBanSpeakTime1().compareTo(newGame.getStartTime()) != 1
					|| newGame.getBanSpeakTime1().compareTo(newGame.getEndTime()) != -1) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error10"), null));
					return;
	
				}
	
			}
			//ゲームを新規作成する
			newBuildLiarGameService.insertGame(newGame);
			
			//ログインユーザーのゲーム中にする
			loginUser.setIsGame("1");
			//メッセージ出力
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(resourceBundle.getString("message12")));
	
			/**
			 * twitter宣伝フラグがTrueなら宣伝のダイアログを開く
			 */
			if(advertiseTwitterFlag) {
				//宣伝URLの作成
				String advertiseUrl = "https://twitter.com/intent/tweet?text="
						+ "【"+langLabelBean.getLabel("create_game_url")+" "+"～"+newGame.getGameName()+"～】"
						//+System.getProperty("line.separator")
						+ resourceBundle.getString("hostUrl")+"faces/liar/explainLiarGame.xhtml?gameId="+(String) loginUser.getGameId();
				//javascript実行
				PrimeFaces.current().executeScript("window.open('"+advertiseUrl+"');");
				//FacesContext.getCurrentInstance().getExternalContext().redirect(advertiseUrl);
	
			}
		} catch (Exception e) {
			new OutLogException(e.getMessage());
		}

	}

	public void clear() {
		newGame = new LiarGameDto();
	}
	
	public void updateSession() {
		if(newGame.getOneDaySessionTimes().equals("1")){
			return;
		}
		newGame.setBanSpeakMinutes("");
		newGame.setSeparateTime("");
	}
	

}
