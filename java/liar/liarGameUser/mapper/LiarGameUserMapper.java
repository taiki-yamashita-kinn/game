package liar.liarGameUser.mapper;

import java.util.List;

import common.dto.MessageDto;
import common.dto.UserDto;

public interface LiarGameUserMapper {

	UserDto selectUser(String userId);

	List<MessageDto> selectMessages(String gameId, String userId);
}
