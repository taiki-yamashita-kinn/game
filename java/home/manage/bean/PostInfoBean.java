package home.manage.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import home.manage.dto.PostInfoDto;
import home.manage.service.PostInfoService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class PostInfoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PostInfoService postInfoService;

	@Getter
	@Setter
	private PostInfoDto postInfo;

	@PostConstruct
	public void init() {
		postInfo = new PostInfoDto();
	}

	public void registerInfo() {
		postInfoService.registerInfo(postInfo);

		//登録成功メッセージ
		FacesContext.getCurrentInstance().
		addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "投稿に成功しました", null));
	}

}
