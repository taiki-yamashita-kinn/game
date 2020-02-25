package home.liarGameList.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import common.dto.LiarGameDto;
import home.liarGameList.service.LiarGameListService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class LiarGameListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private List<LiarGameDto> gameList;

	@Inject
	private LiarGameListService liarGameListService;

	@Getter
	@Setter
	private String filterData = StringUtils.EMPTY;

	@PostConstruct
	public void init() {
		gameList = liarGameListService.selectAllGame(filterData);
	}


	public void gameFilter() {
		gameList = liarGameListService.selectAllGame(filterData);
	}

}
