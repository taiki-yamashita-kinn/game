package common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import common.dto.LoginInfo;
import common.dto.UserDto;

public interface CommonMapper {
	UserDto selectUser(String userId);

	LoginInfo selectLoginUser(String userId);

	void deleteUser(String userId);

	void stopUser(String userId);

	void deleteUserMessage(String userId);

	void deleteGame(String gameId);

	void deleteChat(String gameId);

	void deleteGameMessage(String gameId);

	void deleteJoinGame(String gameId);

	void updateSpeakFlag(@Param("flag") String flag, @Param("gameId") String gameId);

	void updateGameEndUser(@Param("gameId") String gameId) ;

	/**
	 * ゲームに参加しているユーザーを挿入
	 * @param userId
	 * @param gameId
	 */
	void insertJoin(@Param("userId") String userId, @Param("gameId") String gameId);

	/**
	 * 多言語のラベル一覧を取得
	 * @return
	 */
	List<Map<String, Object>> selectLabel();
	
	String selectJoinUser(@Param("userId") String userId, @Param("gameId") String gameId);
}
