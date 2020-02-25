package liar.explainLiarGame.service;

import common.dto.LiarGameDto;

public interface ExplainLiarGameService {
	LiarGameDto selectLiarGame(String gameId);

	boolean joinGame(String userId, String gameId);

}
