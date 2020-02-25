package home.manage.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import common.dto.LoginInfo;
import common.exception.OutLogException;
import home.manage.dto.PostInfoDto;
import home.manage.mapper.PostInfoMapper;
import home.manage.service.PostInfoService;

@RequestScoped
public class PostInfoServiceImpl implements PostInfoService{

	@Inject
	private SqlSession sqlSession;

	@Inject
	private LoginInfo loginInfo;

	@Override
	public void registerInfo(PostInfoDto postInfo) {

		Map<String, Object> condition = new HashMap<>();
		condition.put("userId", loginInfo.getUserId());
		condition.put("text",  postInfo.getText());
		condition.put("url", postInfo.getUrl());
		condition.put("title", postInfo.getTitle());
		try {
			if(postInfo.getPostFlag() == 1) {
				sqlSession.getMapper(PostInfoMapper.class).registerInfo(condition);
			}else if(postInfo.getPostFlag() == 2) {
				sqlSession.getMapper(PostInfoMapper.class).registerHelp(condition);
			}
			sqlSession.commit();
		}catch(Exception e) {
			new OutLogException(e.getMessage());
			sqlSession.rollback();
		}
	}

}
