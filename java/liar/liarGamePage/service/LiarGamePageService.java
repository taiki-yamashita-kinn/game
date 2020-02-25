package liar.liarGamePage.service;

import java.util.List;

import common.dto.LiarGameDto;
import liar.liarGamePage.dto.LiarGamePageDto;
import liar.liarUserList.dto.LiarUserListDto;

public interface LiarGamePageService {

	LiarGameDto selectGame(String gameId);

	List<LiarUserListDto> selectUserList(String gameId);

	List<LiarGamePageDto> selectChatUser(String gameId,String userId);

	void insertStatus(String userId, String gameId, String status);

	void leaveGame(String userId, String gameId);
}
