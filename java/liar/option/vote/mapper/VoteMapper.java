package liar.option.vote.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import liar.option.vote.dto.VoteDto;

public interface VoteMapper {

	void deleteVote(@Param("userId") String userId, @Param("gameId") String gameId);

	void insertVote(@Param("userId") String userId, @Param("gameId") String gameId,@Param("value") String value);

	List<VoteDto> selectVote(@Param("userId") String userId, @Param("gameId") String gameId);

}
