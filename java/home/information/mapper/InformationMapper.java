package home.information.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import home.information.dto.InformationDto;

public interface InformationMapper {

	List<InformationDto> selectInformationMessage();

	/**
	 * お知らせを削除する
	 * @param condition
	 */
	void deleteInfo(@Param("infoId") String infoId);

}
