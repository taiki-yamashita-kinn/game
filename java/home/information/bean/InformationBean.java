package home.information.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import home.information.dto.InformationDto;
import home.information.service.InformationService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class InformationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private List<InformationDto> informationMessageList;

	@Inject
	private InformationService informationService;

	@PostConstruct
	public void init() {
		informationMessageList = informationService.selectInformationMessage();
	}
}

