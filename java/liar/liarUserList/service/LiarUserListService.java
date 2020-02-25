package liar.liarUserList.service;

import java.util.List;

import liar.liarUserList.dto.LiarUserListDto;

public interface LiarUserListService {

	List<LiarUserListDto> selectJoinUser(String gameId);

	boolean registerChattRoom(List<LiarUserListDto> chatRoomList, String gameId, String roomName);
}
