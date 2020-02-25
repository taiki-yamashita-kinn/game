package home.signUp.service;

import java.util.Map;

public interface SignUpService {
	
	boolean confirm(String userName);

	void googleRegister(Map<String, String> condition);
}
