package liar.editLiarGame.service;

import common.dto.LiarGameDto;

public interface EditLiarGameService {
	void updateGame(LiarGameDto newGame) throws Exception;

	LiarGameDto selectLiarGame(String gameId);

}
