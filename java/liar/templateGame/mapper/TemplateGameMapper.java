package liar.templateGame.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import liar.templateGame.dto.TemplateGameDto;

public interface TemplateGameMapper {

	//テンプレートのゲームを取得する
	List<TemplateGameDto> selectTemplateGame();

	TemplateGameDto selectEvent(@Param("id") String id);

}
