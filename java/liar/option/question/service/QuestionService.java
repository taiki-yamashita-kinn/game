package liar.option.question.service;

import java.util.List;

import liar.option.question.dto.QuestionDto;

public interface QuestionService {
	/**
	 * 投票する
	 * @param value
	 * @param gameId
	 */
	void question(String value, String gameId);
	
	List<QuestionDto> getQuestion(String userId,String gameId);
	
	void updateQuestion(String gameId, String yesNo);

}
