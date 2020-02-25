package liar.explainLiarGame.mapper;

import org.apache.ibatis.annotations.Param;

import common.dto.LiarGameDto;

public interface ExplainLiarGameMapper {

	/**
	 * 村説明データを取得
	 * @param gameId
	 * @return
	 */
	LiarGameDto selectLiarGame(@Param("gameId") String gameId);

	/**
	 * ゲームに参加したことを更新するUPDATE
	 * @param userId
	 * @param gameId
	 */
	void joinGame(@Param("userId") String userId, @Param("gameId") String gameId);

	/**
	 * ゲーム参加人数を更新するUPDATE
	 * @param gameId
	 */
	void joinUserPlus(@Param("gameId") String gameId);



}
