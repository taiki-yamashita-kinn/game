package home.liarGameList.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import common.dto.LiarGameDto;

public interface LiarGameListMapper {

	List<LiarGameDto> selectAllGame(@Param("filterData") String filterData);
}
