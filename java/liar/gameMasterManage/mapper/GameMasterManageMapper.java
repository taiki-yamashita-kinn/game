package liar.gameMasterManage.mapper;

import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.ibatis.annotations.Param;

import liar.gameMasterManage.dto.GameMasterManageDto;
import liar.gameMasterManage.dto.VoteDto;

public interface GameMasterManageMapper {
	List<SelectItem> selectUserList(@Param("gameId") String gameId);

	List<GameMasterManageDto> selectMessageList(Map<String, Object> condition);

	List<VoteDto> selectVoteList(Map<String, Object> condition);
}
