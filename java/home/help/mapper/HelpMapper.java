package home.help.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import home.help.dto.HelpDto;

public interface HelpMapper {

	List<HelpDto> selectHelpMessage(@Param("filterData") String filterData);

}
