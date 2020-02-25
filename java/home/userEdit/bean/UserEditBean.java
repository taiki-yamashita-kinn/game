package home.userEdit.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.FileUploadEvent;

import common.dto.LoginInfo;
import common.exception.OutLogException;
import common.service.interfaces.common.CommonService;
import common.util.path.ImageProperty;
import home.userEdit.dto.UserEditDto;
import home.userEdit.service.UserEditService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class UserEditBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private UserEditDto updateUser;

	@Inject
	private UserEditService userEditService;

	@Inject
	private LoginInfo loginInfo;

	@Inject
	private CommonService commonService;
	
	//プロパティファイル読み込み
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");
	

	@PostConstruct
	public void init()  {
		updateUser = userEditService.selectUser(loginInfo.getUserId());
	}

	//画像アップロード
	public void handleUpload(FileUploadEvent event) throws IOException {
		try {
	        FacesMessage message = new FacesMessage(resourceBundle.getString("message10"), event.getFile().getFileName() + " is uploaded.");

	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, message);
	        
	        File saveFile = new File(ImageProperty.imagePath+"userImage"+updateUser.getUserId()+".png");
	        //ファイル保存
	        ImageIO.write(ImageIO.read(event.getFile().getInputstream()), "png", saveFile);

			updateUser.setPicturePath(ImageProperty.imagePath+"userImage"+updateUser.getUserId()+".png");

	        //imageChange(event.getFile().getInputstream(),1);

		}catch(Exception e){
			new OutLogException(e.getMessage());
		}
		
    }
	//ユーザー登録
	public void update() {
		userEditService.update(updateUser);

		LoginInfo loginInfoDb = commonService.selectLoginUser(loginInfo.getUserId());
		
		try {
			BeanUtils.copyProperties(loginInfo, loginInfoDb);

			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IllegalAccessException e) {
			new OutLogException(e.getMessage());
		} catch (InvocationTargetException e) {
			new OutLogException(e.getMessage());
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}


	}

}
