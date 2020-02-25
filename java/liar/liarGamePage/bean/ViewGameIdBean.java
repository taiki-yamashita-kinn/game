package liar.liarGamePage.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class ViewGameIdBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		gameId = (String)FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("gameId");
	}

	/**
	 * LiarGameのID
	 */
	@Getter
	@Setter
	private String gameId = StringUtils.EMPTY;


}
