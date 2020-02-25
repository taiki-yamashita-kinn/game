package liar.gameMasterManage.service;

import java.util.List;

import javax.faces.model.SelectItem;

import liar.gameMasterManage.dto.GameMasterManageDto;
import liar.gameMasterManage.dto.VoteDto;

public interface GameMasterManageService {

	List<SelectItem> selectUserList(String gameId);

	List<GameMasterManageDto> selectGameMessage(GameMasterManageDto searchData);

	List<VoteDto> selectVoteData(VoteDto voteData);
}
