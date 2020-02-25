package liar.liarChat.bean;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class GameIdBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {

		Map<String, String> param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(param.containsKey("gameId")) {
			gameId = param.get("gameId");
		}
		if(param.containsKey("roomId")) {
			roomId = param.get("roomId");
		}
	}

	/**
	 * LiarGameのID
	 */
	@Getter
	@Setter
	private String gameId = StringUtils.EMPTY;

	/**
	 * 部屋のID
	 */
	@Getter
	@Setter
	private String roomId = StringUtils.EMPTY;

}
