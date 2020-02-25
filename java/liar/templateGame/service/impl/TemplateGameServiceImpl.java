package liar.templateGame.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.bean.LangLabelBean;
import common.exception.OutLogException;
import liar.templateGame.dto.TemplateGameDto;
import liar.templateGame.mapper.TemplateGameMapper;
import liar.templateGame.service.TemplateGameService;

@RequestScoped
public class TemplateGameServiceImpl implements TemplateGameService {

	@Inject
	private SqlSession sqlSession;

	@Inject
	private LangLabelBean langLabelBean;

	@Override
	public List<TemplateGameDto> selectTemplateGame() {
		List<TemplateGameDto> results = new ArrayList<>();
		try {
			results = sqlSession.getMapper(TemplateGameMapper.class).selectTemplateGame();
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;

	}

	@Override
	public TemplateGameDto selectEvent(String id) {
		TemplateGameDto result = new TemplateGameDto();
		try {
			result = sqlSession.getMapper(TemplateGameMapper.class).selectEvent(id);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return result;
	}


}
