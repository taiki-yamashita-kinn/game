package liar.option.ruleFlagChange.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;

import common.dto.LiarGameDto;
import liar.liarChat.bean.GameIdBean;
import liar.liarGamePage.service.LiarGamePageService;
import liar.option.ruleFlagChange.service.RuleFlagChangeService;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class RuleFlagChangeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String value =StringUtils.EMPTY;

	@Inject
	private RuleFlagChangeService ruleFlagChangeService;

	//ゲームIDをViewDtoで持っている
	@Inject
	private GameIdBean gameIdBean;

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

		//初期処理の時のみ処理を行う
		 if (FacesContext.getCurrentInstance().isPostback()) {
			 return;
		 }
		//接続をopenする
		 PrimeFaces.current().executeScript("PF('subscriber').connect('/chat')");

	}


	/**
	 * フラグを変更する
	 */
	public void flagChange() {
		//フラグを変える
		ruleFlagChangeService.flagChange(value, gameIdBean.getGameId());

		//メッセージ出力
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("投票を完了しました"));

	}

	//daialogをcloseする
    public void closeDialog() {
    	PrimeFaces.current().dialog().closeDynamic("");
    }

}
