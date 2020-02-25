package common.service.interfaces.timer;

import common.dto.LiarGameDto;
/**
 * ゲームのスケジュールを登録するクラス
 * @author taita
 *
 */
public interface GameScheduleService {

	/**
	 * statusでゲームをスケジューリングする
	 */
	void gameStatusSchedule(String userId,String gameId, String status) throws Exception;


	/**
	 * ゲームスタートのスケジューリング
	 * @param newGame
	 * @throws Exception
	 */
	void gameStart(LiarGameDto newGame) throws Exception;

	/**
	 * ゲーム終了のスケジューリング
	 * @param newGame
	 * @throws Exception
	 */
	void gameEnd(LiarGameDto newGame) throws Exception;

	/**
	 * 発言禁止時間のスケジューリング
	 * @param newGame
	 * @throws Exception
	 */
	void gameBanSpeak1(LiarGameDto newGame) throws Exception;

	/**
	 * 発言禁止時間のスケジューリング
	 * @param newGame
	 * @throws Exception
	 */
	/*void gameBanSpeak2(LiarGameDto newGame) throws Exception;*/

	/**
	 *
	 * @param newGame
	 * @throws Exception
	 */
	void gameReleaseSpeak(LiarGameDto newGame) throws Exception;

}
