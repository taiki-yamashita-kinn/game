package liar.liarUserList.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import common.dto.ChatRoomDto;
import liar.liarUserList.dto.LiarUserListDto;

public interface LiarUserListMapper {

	//参加済みユーザーを取得
	List<LiarUserListDto> selectJoinUser(@Param("gameId") String gameId,@Param("userId") String userId);

	//ルームIDを取得する
	String selectRoomId(String gameId);

	//ログインユーザーが参加してる部屋を取得する
	List<ChatRoomDto> selectLoginRoom(Map<String, Object> condition);

	//部屋を追加する
	void insertChatRoom(Map<String, Object> condition);
	
	//部屋を追加する
	void insertChatRoomManage(Map<String, Object> condition);
}
