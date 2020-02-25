package liar.gameMasterManage.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import common.exception.OutLogException;
import liar.gameMasterManage.dto.GameMasterManageDto;
import liar.gameMasterManage.dto.VoteDto;
import liar.gameMasterManage.service.GameMasterManageService;
import liar.liarChat.bean.GameIdBean;
import liar.option.question.dto.QuestionDto;
import liar.option.question.service.QuestionService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class GameMasterManageBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//ゲームIDをViewDtoで持っている
	@Inject
	private GameIdBean gameIdBean;

	@Getter
	@Setter
	private List<SelectItem> userList;

	@Getter
	@Setter
	private GameMasterManageDto searchData;

	@Getter
	@Setter
	private VoteDto voteData;
	
	@Getter
	@Setter
	private List<GameMasterManageDto> messageList;

	@Inject
	private GameMasterManageService gameMasterManageService;

	@Getter
	@Setter
	private List<VoteDto> voteList;
	
	@Getter
	@Setter
	private List<QuestionDto> questionList;
	
	@Inject
	private QuestionService questionService;

	//プロパティファイル読み込み
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

	@PostConstruct
	public void init() {
		searchData = new GameMasterManageDto();
		voteData = new VoteDto();
		userList = gameMasterManageService.selectUserList(gameIdBean.getGameId());

		messageList = new ArrayList<>();
		voteList = new ArrayList<>();
		questionList = new ArrayList<>();
		questionList = questionService.getQuestion("",gameIdBean.getGameId());
	}

	/**
	 * ユーザ検索
	 */
	public void search() {
		searchData.setGameId(gameIdBean.getGameId());
		messageList = gameMasterManageService.selectGameMessage(searchData);
		if(messageList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(resourceBundle.getString("message11")));
		}
	}

	/**
	 * 投票検索
	 */
	public void search2() {
		voteData.setGameId(gameIdBean.getGameId());
		voteList = gameMasterManageService.selectVoteData(voteData);
		if(voteList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage("growl2", new FacesMessage(resourceBundle.getString("message11")));
		}
	}
	
	/**
	 * 質問にyes noで答える
	 */
	public void updateYesNo(String id, String yesNo) {
		//質問にYES NOで答える
		questionService.updateQuestion(id, yesNo);
	}

	public void clear() {
		messageList.clear();
		voteList.clear();

	}

	//戻る
    public void backGamePage() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("liarGamePage.xhtml?gameId="+gameIdBean.getGameId());
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
    }

}
