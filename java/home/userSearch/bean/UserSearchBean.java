package home.userSearch.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import common.dto.UserDto;
import home.userSearch.dto.UserSearchDto;
import home.userSearch.service.UserSearchService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class UserSearchBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private UserSearchDto selectUser;

	@Getter
	@Setter
	private List<UserDto> userList;

	@Inject
	private UserSearchService userSearchService;

	//@Inject
	//private GoogleSheetsService googleSheetsService;

	@PostConstruct
	public void init() {
		selectUser = new UserSearchDto();
	}

	public void search() {
		userList = userSearchService.searchUser(selectUser);

	}

/*	public String userPageTrans() {
		return "userPage?faces-redirect=true";
	}*/
	
	
}
