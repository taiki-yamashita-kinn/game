package liar.liarGamePage.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import liar.liarGamePage.dto.LiarGamePageDto;
import liar.liarUserList.dto.LiarUserListDto;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class TabUserListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	@PostConstruct
	public void init() {
		viewUsers = new ArrayList<>();
		chatUserList = new ArrayList<>();
	}

}
