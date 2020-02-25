package liar.templateGame.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import liar.templateGame.dto.TemplateGameDto;
import liar.templateGame.service.TemplateGameService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class TemplateGameBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private List<TemplateGameDto> templateGameList;

	@Getter
	@Setter
	private String templateId;

	@Getter
	@Setter
	private TemplateGameDto templateGame;

	@Inject
	private TemplateGameService templateGameService;

	@PostConstruct
	public void init() {
		//テンプレートゲーム一覧を取得する
		templateGameList = templateGameService.selectTemplateGame();
	}

	/**
	 * イベントでテンプレートゲームの説明・時間などを取得する
	 */
	public void selectEventGame() {
		templateGame = templateGameService.selectEvent(templateId);
	}


}
