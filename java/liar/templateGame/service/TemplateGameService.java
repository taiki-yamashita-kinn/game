package liar.templateGame.service;

import java.util.List;

import liar.templateGame.dto.TemplateGameDto;

public interface TemplateGameService {

	List<TemplateGameDto> selectTemplateGame();

	TemplateGameDto selectEvent(String id);
}
