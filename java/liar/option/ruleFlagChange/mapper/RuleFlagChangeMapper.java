package liar.option.ruleFlagChange.mapper;

import java.util.Map;

public interface RuleFlagChangeMapper {

	void insertFlagChange(Map<String, Object> condition);

	Map<String, Object> selectFlagChange(Map<String, Object> condition);

	void updateUserFlagChange(Map<String, Object> condition);

}
