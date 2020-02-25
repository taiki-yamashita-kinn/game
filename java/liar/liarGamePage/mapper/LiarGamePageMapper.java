package liar.liarGamePage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import common.dto.LiarGameDto;
import liar.liarGamePage.dto.LiarGamePageDto;
import liar.liarUserList.dto.LiarUserListDto;

public interface LiarGamePageMapper {
	LiarGameDto selectGame(@Param("gameId") String gameId,@Param("userId") String userId);

	List<LiarUserListDto> selectUserList(String gameId);

	List<LiarGamePageDto> selectChatUser(@Param("gameId") String gameId,@Param("userId") String userId);

	void insertStatus(@Param("userId") String userId,@Param("gameId") String gameId,@Param("status") String status);

	void leaveGame(@Param("userId") String userId);

	void decreaseJoin(@Param("gameId")  String gameId);

	void deleteJoinGame(@Param("userId") String userId,@Param("gameId")  String gameId);

}
