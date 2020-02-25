package liar.liarChat.bean;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import common.dto.ChatRoomDto;
import common.dto.LoginInfo;
import common.dto.MessageDto;
import common.exception.OutLogException;
import common.service.interfaces.common.CommonService;
import common.util.chat.ChatServerEndPoint;
import common.util.path.ImageProperty;
import liar.liarChat.service.LiarChatService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class LiarChatBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private LoginInfo loginUser;

	/**
	 * チャットの情報
	 */
	@Getter
	@Setter
	private ChatRoomDto chatRoom;

	/**
	 * チャットのテキスト
	 */
	@Getter
	@Setter
	private MessageDto message;

	@Inject
	private LiarChatService liarChatService;

	//ゲームIDをViewDtoで持っている
	@Inject
	private GameIdBean gameIdBean;
	
	/**
	 * メッセージのリスト
	 */
	@Getter
	@Setter
	private List<MessageDto> messageList;
	
	//@Inject
	//private MessageMap messageMap;
	
	@Inject
	private MessageInterface messageInterface;
	
	@Inject
	private CommonService commonService;
	
	//プロパティファイル読み込み
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

	@PostConstruct
	public void init() {
		try{
			//メッセージの初期化
			message = new MessageDto();
			
			chatRoom = new ChatRoomDto();
    	    
    	    messageList = new ArrayList<>();
    	    
	        //チャット取得
	        getChat();
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
	}

	/**
	 * システムメッセージをデータベースに登録する
	 * @param registerMessage 登録するメッセージ
	 * @param gameId ゲームのID
	 */
	public void insertSystemMessage(MessageDto registerMessage, String gameId) {
		registerMessage.setGameId(gameId);
		//会場のルームIDを送る
		registerMessage.setRoomId("0");
		//メッセージをデータベースに登録する
        liarChatService.insertMessage(registerMessage);
        //messageList.add(0,message);
        ChatServerEndPoint.broadCast("ajaxListenerEvent",gameIdBean.getGameId()+"_"+gameIdBean.getRoomId());
		registerMessage.setMessage("");
	}
	
	// ユーザーがチャット機能で発言するメソッド
	public void sendText() {
		try {
			MessageDto messageData = new MessageDto();
			messageData.setMessage(message.getMessage());
			messageData.setUserId(loginUser.getUserId());
			messageData.setUserName(loginUser.getUserName());
			messageData.setRoomId(gameIdBean.getRoomId());
			messageData.setGameId(gameIdBean.getGameId());

			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// LocalDateTime localDateTime = LocalDateTime.now();
			// message.setSpeakDate(sdf.format(Date.from(ZonedDateTime.of(localDateTime,
			// ZoneId.systemDefault()).toInstant())));

			// メッセージを登録する
			registerMessage(messageData);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			LocalDateTime localDateTime = LocalDateTime.now();
			messageData.setSpeakDate(
					sdf.format(Date.from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toInstant())));

			if (new File(ImageProperty.imagePath + "userImage" + messageData.getUserId() + ".png").exists()) {
				messageData.setPicturePath(ImageProperty.imagePath + "userImage" + messageData.getUserId() + ".png");
			}
			
			messageInterface.pushMap(messageData);
			ChatServerEndPoint.broadCast("ajaxListenerEvent", gameIdBean.getGameId() + "_" + gameIdBean.getRoomId());
		} catch (Exception e) {
			new OutLogException(e.getMessage());
		}
    }
    
    
    public void updateChat() {
    	MessageDto messageData = messageInterface.getPushMap(gameIdBean.getRoomId()+","+gameIdBean.getGameId());
    	if (messageData.getUserId().equals(chatRoom.getUserMasterId())) {
			messageData.setStyleClass("chatCenter");
		} else if (messageData.getUserId().equals(loginUser.getUserId())) {
			messageData.setStyleClass("chatRight");
		} else {
			messageData.setStyleClass("chatLeft");
		}
    	messageList.add(0,messageData);
    }
    
    /**
          * チャットの内容を取得する
     */
    public void getChat() {
    	try {
    		if(StringUtils.isEmpty(gameIdBean.getRoomId()) || 
    				StringUtils.isEmpty(gameIdBean.getGameId())) {
    			FacesContext.getCurrentInstance().getExternalContext().
				redirect(resourceBundle.getString("hostUrl"));
				return;
			}
    		
    		//ゲームが発言禁止かどうか取得する
    		chatRoom = liarChatService.selectGame(gameIdBean.getGameId());

    		//chatRoomにルームナンバーをセットする
    		chatRoom.setRoomId(gameIdBean.getRoomId());
    		
    		String roomName = liarChatService.selectRoomName(chatRoom);
    		
    		//ゲームが非公開でユーザが参加していない、会場じゃないならリダイレクトする
    		if(chatRoom.getGameOpen().equals("0") && StringUtils.isEmpty(roomName) && !chatRoom.getRoomId().equals("0")) {
				FacesContext.getCurrentInstance().getExternalContext().
				redirect(resourceBundle.getString("hostUrl"));
				return;
    		}
    		
    		if(chatRoom.getRoomId().equals("0")) {
    			chatRoom.setRoomName("会場");
    		} else {
    			chatRoom.setRoomName(roomName);
    		}
    		
    	    //部屋のmessageの一覧を取得する
    	    messageList = liarChatService.selectMessage(chatRoom);

       		//取得メッセージが0件なら処理をしない
    		if(messageList.size() == 0) {
    			return;
    		}

    		//ユーザマスタだった場合投稿できるようにする
    		if(chatRoom.getUserMasterId().equals(loginUser.getUserId())) {
    			chatRoom.setSpeakFlag("0");
    		}
    		
    		//ゲームに参加していないユーザの場合speakフラグを0にする
    		if(commonService.selectJoinUser(gameIdBean.getGameId()).equals("0")) {
    			chatRoom.setSpeakFlag("1");
    		}
    		
    	}catch(Exception e) {
			new OutLogException(e.getMessage());
    	}
    }
    
    //メッセージを登録する（パラメータ）
    private void registerMessage(MessageDto registerMessage){
    	//メッセージをデータベースに登録する
        liarChatService.insertMessage(registerMessage);
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
