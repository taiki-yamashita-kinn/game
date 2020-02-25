package common.service.interfaces.common;

import java.util.List;
import java.util.Map;

import common.dto.LoginInfo;
import common.dto.UserDto;


public interface CommonService {

	/**
	 *ユーザーを取得する
	 * @param userId
	 * @return
	 */
	UserDto selectUser(String userId) ;
	/**
	 *ログインユーザーを取得する
	 * @param userId
	 * @return
	 */
	LoginInfo selectLoginUser(String userId) ;

	/**
	 *ユーザーを削除する
	 * @param userId
	 */
	void deleteUser(String userId) ;

	/**
	 *ユーザーを停止する
	 * @param userId
	 */
	void stopUser(String userId) ;

	/**
	 *チャットメッセージを削除する
	 * @param gameId
	 */
	void deleteGameChatMessage(String gameId) ;

	/**
	 * ゲームの発言禁止を解除したり禁止したりする
	 * @param flag
	 * @param gameId
	 */
	void updateSpeakFlag(String flag, String gameId) ;

	/**
	 * ゲームの発言禁止を解除したり禁止したりする
	 * @param flag
	 * @param gameId
	 */
	void updateGameEndUser(String gameId) ;

	/**
	 *
	 * @return
	 */
	List<Map<String,Object>> selectLabel();

	String selectJoinUser(String gameId);
}
