package home.manage.mapper;

import java.util.List;
import java.util.Map;

import common.dto.UserDto;

public interface ManageMapper {

	List<UserDto> searchUser(Map<String, Object> condition);

}
