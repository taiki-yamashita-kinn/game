package home.login.service;

import common.dto.LoginInfo;

public interface LoginService {
	LoginInfo confirmUser(String userName, String password);
	
	LoginInfo confirmGoogleLogin(String googleId);

}
