package home.manage.service;

import java.util.List;

import common.dto.UserDto;
import home.manage.dto.ManageDto;

public interface ManageService {
	List<UserDto> searchUser(ManageDto user);

}
