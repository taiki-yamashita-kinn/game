package home.userSearch.mapper;

import java.util.List;
import java.util.Map;

import common.dto.UserDto;

public interface UserSearchMapper {

	List<UserDto> searchUser(Map<String, Object> condition);
}
