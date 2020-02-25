package home.login.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import common.dto.LoginInfo;

public interface LoginMapper {
	LoginInfo confirmLoginUser(Map<String, Object> condition);
	
	LoginInfo confirmGoogleLoginUser(@Param("googleId") String googleId);

}
