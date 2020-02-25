package liar.editLiarGame.mapper;

import java.util.Map;

import common.dto.LiarGameDto;

public interface EditLiarGameMapper {

	void updateLiarGame(Map<String, Object> condition);

	/**
	 * ゲームのURLを更新
	 * @param userId
	 * @return
	 */
	void updateGameUrl(Map<String, Object> condition);

	LiarGameDto selectLiarGame(String gameId);


}
