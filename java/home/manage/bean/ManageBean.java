package home.manage.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import common.dto.UserDto;
import common.service.interfaces.common.CommonService;
import home.manage.dto.ManageDto;
import home.manage.service.ManageService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class ManageBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ManageService ManageService;

	@Inject
	private CommonService commonService;

	@Getter
	@Setter
	private ManageDto selectUser;

	@Getter
	@Setter
	private List<UserDto> userList;


	@PostConstruct
	public void init() {
		selectUser = new ManageDto();
		userList= new ArrayList<>();
	}

	public void search() {
		userList = ManageService.searchUser(selectUser);
		System.out.println(4);
	}

	public void deleteUser(String userId) {
		commonService.deleteUser(userId);
		search();
	}

	public void stopUser(String userId) {
		commonService.stopUser(userId);
		search();
	}

}
