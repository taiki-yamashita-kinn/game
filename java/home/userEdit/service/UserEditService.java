package home.userEdit.service;

import home.userEdit.dto.UserEditDto;

public interface UserEditService {
	UserEditDto selectUser(String userId);
	void update(UserEditDto user);
}
