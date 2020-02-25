package liar.liarGameUser.service;

import java.util.List;

import common.dto.MessageDto;
import common.dto.UserDto;

public interface LiarGameUserService {

	UserDto selectUser(String userId);

	List<MessageDto> selectMessages(String gameId, String userId);
}
