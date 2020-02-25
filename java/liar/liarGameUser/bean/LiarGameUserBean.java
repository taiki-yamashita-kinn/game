package liar.liarGameUser.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import common.dto.MessageDto;
import common.dto.UserDto;
import liar.liarGameUser.service.LiarGameUserService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class LiarGameUserBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private UserDto oneUser;

	@Getter
	@Setter
	private List<MessageDto> messages;

	String userId;

	String gameId;

	@Inject
	private LiarGameUserService liarGameUserService;

	@PostConstruct
	public void init() {
		oneUser = new UserDto();
		userId = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("userId");
		gameId = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("gameId");
		oneUser = liarGameUserService.selectUser(userId);
		messages = liarGameUserService.selectMessages(gameId, userId);
	}

}
