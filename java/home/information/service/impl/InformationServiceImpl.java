package home.information.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.exception.OutLogException;
import home.information.dto.InformationDto;
import home.information.mapper.InformationMapper;
import home.information.service.InformationService;

@RequestScoped
public class InformationServiceImpl implements InformationService{

	@Inject
	private SqlSession sqlSession;

	@Override
	public List<InformationDto> selectInformationMessage() {
		List<InformationDto> results = new ArrayList<>();
		try {
			results = sqlSession.getMapper(InformationMapper.class).selectInformationMessage();
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}

}
