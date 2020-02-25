package liar.newBuildLiarGame.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface NewBuildLiarGameMapper {
	void insertLiarGame(Map<String, Object> condition);

	void insertSystemMessage(Map<String, Object> condition);

	void updateUser(@Param("userId") String userId, @Param("gameId") String gameId);

	/**
	 * ゲームのURLを消去
	 * @param userId
	 * @return
	 */
	void deleteGameUrl(@Param("gameId") String gameId);

	/**
	 * ゲームのURLを挿入
	 * @param userId
	 * @return
	 */
	void insertGameUrl(Map<String, Object> condition);

}
