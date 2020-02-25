package liar.option.ruleFlagChange.service;

public interface RuleFlagChangeService {
	/**
	 * フラグを変える
	 * @param value
	 * @param gameId
	 */
	void flagChange(String value, String gameId);

	void insertChange(String value, String message, String gameId);

}
