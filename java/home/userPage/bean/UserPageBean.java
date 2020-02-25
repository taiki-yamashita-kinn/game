package home.userPage.bean;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;

import common.dto.UserDto;
import common.exception.OutLogException;
import common.service.interfaces.common.CommonService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class UserPageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String userId;

	@Getter
	@Setter
	private BarChartModel barModel;

	//@Inject
	//private LoginInfo loginInfo;

	@Getter
	@Setter
	private UserDto selectUser;

	@Inject
	private CommonService commonService;
	
	//プロパティファイル読み込み
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

	@PostConstruct
	public void init() {

		//new OutLogException("");
		try {
			selectUser = commonService.selectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId"));

			if(selectUser == null){
				FacesContext.getCurrentInstance().getExternalContext().
				redirect(resourceBundle.getString("hostUrl"));
				return;
			}
			
		} catch(Exception e) {
			new OutLogException(e.getMessage());
		}
	}

	
}
