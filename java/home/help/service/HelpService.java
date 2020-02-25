package home.help.service;

import java.util.List;

import home.help.dto.HelpDto;

public interface HelpService {

	List<HelpDto> selectHelpMessage(String filterData);

}
