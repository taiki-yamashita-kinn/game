package home.signUp.service.impl;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import common.exception.OutLogException;
import common.service.interfaces.google.GoogleGmailService;
import common.util.key.EncryDecry;
import home.signUp.mapper.SignUpMapper;
import home.signUp.service.SignUpService;

@RequestScoped
public class SignUpServiceImpl implements SignUpService{

	@Inject
	private SqlSession sqlSession;

	@Inject
	private GoogleGmailService googleGmailService;

	@Override
	public boolean confirm(String userName) {
		String count = "";
		try {
			count = sqlSession.getMapper(SignUpMapper.class).confirmUserName(userName);		
		}catch (Exception e){
			new OutLogException(e.getMessage());
		}
		if(!count.equals("0")) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void googleRegister(Map<String, String> condition) {
		try {
			//Gmailは暗号化して保存
			condition.put("g_account",  EncryDecry.encrypto(condition.get("email").split("@")[0]));
			condition.put("lang", condition.get("locale").equals("ja") ? "0":"1");
			//userのIPアドレスを取得
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
			    ipAddress = request.getRemoteAddr();
			}
			condition.put("ipAddress", ipAddress);
		
			sqlSession.getMapper(SignUpMapper.class).googleRegister(condition);
			sqlSession.commit();
			//googleGmailService.sendGmail(condition);
		}catch (Exception e){
			sqlSession.rollback();
			new OutLogException(e.getMessage());

		}
	}
}
