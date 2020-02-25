package home.liarGameList.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.LiarGameDto;
import common.exception.OutLogException;
import home.liarGameList.mapper.LiarGameListMapper;
import home.liarGameList.service.LiarGameListService;

@RequestScoped
public class LiarGameListServiceImpl implements LiarGameListService {

	@Inject
	private SqlSession sqlSession;

	public List<LiarGameDto> selectAllGame(String filterData) {
		List<LiarGameDto> results = new ArrayList<>();
		try {
			results = sqlSession.getMapper(LiarGameListMapper.class).selectAllGame(filterData);
		}catch(Exception e) {
			new OutLogException(e.getMessage());
		}
		return results;
	}

}
