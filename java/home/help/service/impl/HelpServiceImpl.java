package home.help.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.exception.OutLogException;
import home.help.dto.HelpDto;
import home.help.mapper.HelpMapper;
import home.help.service.HelpService;

@RequestScoped
public class HelpServiceImpl implements HelpService{
	@Inject
	private SqlSession sqlSession;

	@Override
	public List<HelpDto> selectHelpMessage(String filterData) {
		List<HelpDto> results = new ArrayList<>();
		try {
			results = sqlSession.getMapper(HelpMapper.class).selectHelpMessage(filterData);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}
}
