package home.help.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import home.help.dto.HelpDto;
import home.help.service.HelpService;
import lombok.Getter;
import lombok.Setter;
@Named
@ViewScoped
public class HelpBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private List<HelpDto> helpMessageList;

	@Inject
	private HelpService helpService;

	@Getter
	@Setter
	private String filterData= StringUtils.EMPTY;

	@PostConstruct
	public void init() {
		helpMessageList = new ArrayList<>();
		helpMessageList = helpService.selectHelpMessage(filterData);
	}


	public void selectFilterData() {
		helpMessageList = new ArrayList<>();
		helpMessageList = helpService.selectHelpMessage(filterData);
	}
}
