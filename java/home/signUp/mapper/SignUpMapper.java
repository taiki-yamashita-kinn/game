package home.signUp.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SignUpMapper {
	String confirmUserName(@Param("userName") String userName);
	
	void insertUser(Map<String, Object> condition);
	
	void googleRegister(Map<String, String> condition);

}
