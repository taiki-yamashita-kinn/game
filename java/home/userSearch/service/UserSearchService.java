package home.userSearch.service;

import java.util.List;

import common.dto.UserDto;
import home.userSearch.dto.UserSearchDto;

public interface UserSearchService {

	List<UserDto> searchUser(UserSearchDto user);
}
