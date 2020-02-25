package liar.option.question.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import common.dto.LiarGameDto;
import common.dto.LoginInfo;
import common.exception.OutLogException;
import common.util.chat.ChatServerUpdatePageEndpoint;
import liar.liarChat.bean.GameIdBean;
import liar.liarGamePage.service.LiarGamePageService;
import liar.option.question.dto.QuestionDto;
import liar.option.question.service.QuestionService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class QuestionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String value =StringUtils.EMPTY;
	
	@Inject
	private LoginInfo loginUser;

	@Inject
	private QuestionService questionService;

	//ゲームIDをViewDtoで持っている
	@Inject
	private GameIdBean gameIdBean;
	
	@Getter
	@Setter
	private List<QuestionDto> questionList;
	
	//プロパティファイル読み込み
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

	/**
	 * 開催中の画面のゲーム
	 */
	@Getter
	@Setter
	private LiarGameDto game;

	@Inject
	private LiarGamePageService liarGamePageService;


	@PostConstruct
	public void init() {
		//ゲームの取得
		game = liarGamePageService.selectGame(gameIdBean.getGameId());
		
		questionList = questionService.getQuestion(loginUser.getUserId(),gameIdBean.getGameId());
	}


	public void question() {
		//投票する
		questionService.question(value, gameIdBean.getGameId());
		
		questionList = questionService.getQuestion(loginUser.getUserId(),gameIdBean.getGameId());
		
		//chatの更新を行う
		ChatServerUpdatePageEndpoint.broadCast("push",gameIdBean.getGameId());

		//メッセージ出力
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(resourceBundle.getString("message15")));

	}

	//戻る
    public void backGamePage() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../liarGamePage.xhtml?gameId="+gameIdBean.getGameId());
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
    }

}
