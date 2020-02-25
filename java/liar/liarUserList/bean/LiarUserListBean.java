package liar.liarUserList.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import common.dto.LoginInfo;
import common.exception.OutLogException;
import liar.liarUserList.dto.LiarUserListDto;
import liar.liarUserList.service.LiarUserListService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class LiarUserListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private List<LiarUserListDto> viewUsers;

	@Inject
	private LoginInfo loginUser;

	/**
	 * 選択したユーザー一覧
	 */
	@Getter
	@Setter
	private List<LiarUserListDto> selectUsers;

	@Getter
	@Setter
	private String roomName;

	@Getter
	@Setter
	private String gameId;

	@Inject
	private LiarUserListService liarUserListService;

	@PostConstruct
	public void init() {
		try{
			selectUsers = new ArrayList<>();

			viewUsers = new ArrayList<>();
			
			Map<String, String> param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

			//ダイアログのパラメータを取得
			gameId = param.get("gameId");

			//ゲームに参加しているユーザー一覧を取得
			viewUsers = liarUserListService.selectJoinUser(gameId);
			
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}

	}

	//チャットルームを作成する
	public void registerChatRoom(){
		if(StringUtils.isEmpty(roomName)) {
			StringBuilder buffer = new StringBuilder();
			for(LiarUserListDto user:selectUsers) {
				buffer.append(user.getUserName());
				buffer.append(":");
			}
			buffer.append(loginUser.getUserName());
			buffer.append("の部屋");
			roomName = buffer.toString();
		}
		//チャットルームを登録する
		boolean successFlag = liarUserListService.registerChattRoom(selectUsers, gameId, roomName);

		if(successFlag) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("liarGamePage.xhtml?gameId="+gameId);
			} catch (IOException e) {
				new OutLogException(e.getMessage());
			}
		}else {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage
					(FacesMessage.SEVERITY_ERROR,
							"ルームはすでに存在します。登録できません", null));
		}
	}
	
	//戻る
    public void backGamePage() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("liarGamePage.xhtml?gameId="+gameId);
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
    }

}
