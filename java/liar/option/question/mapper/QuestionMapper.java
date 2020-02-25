package liar.option.question.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import liar.option.question.dto.QuestionDto;

public interface QuestionMapper {

	void insertQuestion(@Param("userId") String userId, @Param("gameId") String gameId,@Param("value") String value);

	List<QuestionDto> selectQuestion(@Param("userId") String userId, @Param("gameId") String gameId);

	void updateQuestion(@Param("id") String id, @Param("value") String value);
}
