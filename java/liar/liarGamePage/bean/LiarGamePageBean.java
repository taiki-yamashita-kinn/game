package liar.liarGamePage.bean;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
import liar.liarGamePage.dto.LiarGamePageDto;
import liar.liarGamePage.service.LiarGamePageService;
import liar.liarUserList.dto.LiarUserListDto;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class LiarGamePageBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 開催中の画面のゲーム
	 */
	@Getter
	@Setter
	private LiarGameDto game;

	@Inject
	private LiarGamePageService liarGamePageService;

	/**
	 * 追放ボタンを表示するかどうかのフラグ
	 */
	@Getter
	@Setter
	private boolean kickFlag = false;

	@Inject
	private LoginInfo loginUser;

	/**
	 * ゲームに参加しているユーザー一覧
	 */
	@Getter
	@Setter
	private List<LiarUserListDto> viewUsers;

	/**
	 * チャット作成済みのユーザー一覧
	 */
	@Getter
	@Setter
	private List<LiarGamePageDto> chatUserList;

	@Inject
	private TabUserListBean tabUserListBean;
	

	//ゲームIDをViewDtoで持っている
	@Inject
	private ViewGameIdBean viewGameIdBean;

	/**
	 * eventステータス
	 */
	@Getter
	@Setter
	private String userFlagStatus = StringUtils.EMPTY;
	
	//プロパティファイル読み込み
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

	@PostConstruct
	public void init() {

		game = new LiarGameDto();
		
		try{
			updatePage();

			//ゲームが非公開かつ参加ユーザじゃなかったらリダイレクトする
			if(game.getGameOpen().equals("0") && !loginUser.getGameId().equals(game.getId())) {
				FacesContext.getCurrentInstance().getExternalContext().
				redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
			}
			
			//ログインユーザーが管理者（村作成者なら追放ボタンを表示する）
			if(loginUser.getUserId().equals(game.getUserMasterId())) {
				kickFlag = true;
			}

		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
	}

	/**
	 * ページを更新するソケット通信
	 */
	public void updatePage() {

		try {
			if(StringUtils.isEmpty(viewGameIdBean.getGameId())) {
				FacesContext.getCurrentInstance().getExternalContext().
				redirect(resourceBundle.getString("hostUrl"));
				return;
			}
			//ゲームに参加しているユーザー一覧を取得
			List<LiarUserListDto> viewUsers = liarGamePageService.selectUserList(viewGameIdBean.getGameId());
			
			game = liarGamePageService.selectGame(viewGameIdBean.getGameId());
			
			/**
			 * ログインユーザーがマスターならすべてのルームを取得
			 */
			if(!loginUser.getUserId().isEmpty()) {
				//データベースから、ユーザーが参加しているチャットルーム一覧を取得する
				chatUserList = liarGamePageService.selectChatUser(viewGameIdBean.getGameId(),loginUser.getUserId());
			}else {
				//ログインしていないなら空
				chatUserList = new ArrayList<>();
			}
			tabUserListBean.setChatUserList(chatUserList);
	
			tabUserListBean.setViewUsers(viewUsers);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
			e.printStackTrace();
    	}
	}
	
	public void updateFlag() {
		
	}

	//チャットに参加する
	public void joinChatRoom(String roomNumber) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("liarChat.xhtml?gameId="+viewGameIdBean.getGameId()+
					"&roomId="+roomNumber);
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
	}

	//チャット作成画面を開く
	public void addChat() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("liarUserList.xhtml?gameId="+viewGameIdBean.getGameId());
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}

	}

	//マスター画面
	public void transMaster() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("gameMasterManage.xhtml?gameId="+viewGameIdBean.getGameId());
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
	}


	//オプションのページを開く
	public void optionPage(int flag) {
		try {
			//質問画面に遷移する
			if(flag == 2){
				FacesContext.getCurrentInstance().getExternalContext().redirect("option/question.xhtml?gameId="+viewGameIdBean.getGameId());
	        }
			//投票画面に遷移する
	        else if(flag == 8){
				FacesContext.getCurrentInstance().getExternalContext().redirect("option/vote.xhtml?gameId="+viewGameIdBean.getGameId());
	        }
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}

	}
	/**
	 * コマンドを押したときにリクエストを送る
	 */
	public void pressCommand() {

		liarGamePageService.insertStatus(loginUser.getUserId(), viewGameIdBean.getGameId(), userFlagStatus);

	}

	//ゲーム作成者がゲームから追い出す
	public void kickGame(String userId, String userName) {
		//ゲームを退出する
		liarGamePageService.leaveGame(userId, viewGameIdBean.getGameId());
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("ゲームから"+ userName + "を追い出しました"));
		//socket通信を行う
		ChatServerUpdatePageEndpoint.broadCast("push",viewGameIdBean.getGameId());
	}

	//ゲームから退出する
	public void leaveGame() {
		//ゲームを退出する
		liarGamePageService.leaveGame(loginUser.getUserId(), viewGameIdBean.getGameId());
		//ログインユーザーの設定変更
		loginUser.setIsGame("0");
		//ログインユーザーの設定変更
		loginUser.setGameId("0");
		String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		String servletPath = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();
		try {
			FacesContext.getCurrentInstance().getExternalContext().
			redirect(contextPath+servletPath+"/liarGameList.xhtml");
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}

		//socket通信を行う
		ChatServerUpdatePageEndpoint.broadCast("push",viewGameIdBean.getGameId());
	}

}
