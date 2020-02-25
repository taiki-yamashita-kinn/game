package home.liarGameList.service;

import java.util.List;

import common.dto.LiarGameDto;

public interface LiarGameListService {
	List<LiarGameDto> selectAllGame(String filterData);

}
