package home.login.bean;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import common.dto.LoginInfo;
import common.exception.OutLogException;
import common.service.interfaces.google.GoogleLoginService;
import common.util.key.EncryDecry;
import common.util.key.PasswordUtil;
import home.login.service.LoginService;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@NotNull(message="ユーザ名は必須入力です")
	@Size(max=10)
	private String userName = StringUtils.EMPTY;

	@Getter
	@Setter
	@NotNull(message="パスワードは必須入力です")
	@Size(max=10)
	private String password = StringUtils.EMPTY;

	@Getter
	@Setter
	private String autoUserName = StringUtils.EMPTY;

	@Getter
	@Setter
	private String autoPassword = StringUtils.EMPTY;
	
	@Getter
	@Setter
	private String idToken = StringUtils.EMPTY;

	@Getter
	@Setter
	@Inject
	private LoginInfo loginInfo;

	@Getter
	@Setter
	private boolean autoLoginFlag = false;

	@Inject
	private LoginService loginService;

	public static String flag = StringUtils.EMPTY;

	private FacesContext facesContext = FacesContext.getCurrentInstance();

	private ExternalContext externalContext = facesContext.getExternalContext();

	@Inject
	private GoogleLoginService googleLoginService;
	
	//プロパティファイル読み込み
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

	//ログインして遊んでくださいと出力
	public void outLoginMessage() {
    	if(StringUtils.isEmpty(loginInfo.getUserId())) {
    		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(resourceBundle.getString("message14"), "please login!!! yei!!"));
    	}
	}
	
	public void googleAuthLogin() {
		googleLoginService.googleLogin(idToken);
	}

	//自動ログイン機能実装
	public void autoLogin() {
		if(loginInfo.getUserId().isEmpty()) {
			//ユーザとパスワードが自動ログイン用に設定されていない場合は処理をしない
			if(StringUtils.isEmpty(autoUserName) || StringUtils.isEmpty(autoPassword)) {
				return;
			}
				LoginInfo loginInfoDb = loginService.confirmUser(autoUserName, EncryDecry.decrypto(autoPassword));
				if(loginInfoDb == null) {
				} else {
					try {
						BeanUtils.copyProperties(loginInfo, loginInfoDb);
					} catch (IllegalAccessException e) {
						new OutLogException(e.getMessage());
					} catch (InvocationTargetException e) {
						new OutLogException(e.getMessage());
					}
				}

		}
	}

	public void confirmUser() {
		LoginInfo loginInfoDb = loginService.confirmUser(userName, PasswordUtil.getSafetyPassword(password));
		if(loginInfoDb == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error6"), null));
		} else {
			try {
				BeanUtils.copyProperties(loginInfo, loginInfoDb);
				//自動ログインがONならローカルストレージにユーザー名とパスワードを保存
				//passwordは暗号化
				if(autoLoginFlag) {
					PrimeFaces.current().executeScript("localStorage.setItem('userName','"+ userName+"')");
					PrimeFaces.current().executeScript("localStorage.setItem('password', '"+ EncryDecry.encrypto(PasswordUtil.getSafetyPassword(password))+"')");
				}
				HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance()
		                .getExternalContext().getRequest();
				HttpSession session = ((HttpServletRequest)req).getSession();
				if(session.getAttribute("keepRequestUrl") != null) {					
					externalContext.
					redirect((String) session.getAttribute("keepRequestUrl"));
					session.removeAttribute("keepRequestUrl");
				}else {
					externalContext.
					redirect("index.xhtml");
				}
			} catch (IllegalAccessException e) {
				new OutLogException(e.getMessage());
			} catch (InvocationTargetException e) {
				new OutLogException(e.getMessage());
			} catch (IOException e) {
				new OutLogException(e.getMessage());
			}
		}
	}

	/**
	 * マイページ遷移
	 */
	public void transmypage() {
		String contextPath = externalContext.getRequestContextPath();
		String servletPath = externalContext.getRequestServletPath();
		try {
			externalContext.
			redirect(contextPath+servletPath+"/userPage.xhtml?userId="+loginInfo.getUserId());
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
	}

	/**
	 * マイゲーム遷移
	 */
	public void transmyGame() {
		String contextPath = externalContext.getRequestContextPath();
		String servletPath = externalContext.getRequestServletPath();
		try {
			externalContext.
			redirect(contextPath+servletPath+"/liar/liarGamePage.xhtml?gameId="+loginInfo.getGameId());
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
	}


	/**
	 * 新規登録
	 */
	public void viewSignUp() {
		String contextPath = externalContext.getRequestContextPath();
		String servletPath = externalContext.getRequestServletPath();
        
        try {
			externalContext.
			redirect(contextPath+servletPath+"/signUp.xhtml");
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
    }

	/**
	 * 編集
	 */
	public void viewEdit() {
		String contextPath = externalContext.getRequestContextPath();
		String servletPath = externalContext.getRequestServletPath();
        
        try {
			externalContext.
			redirect(contextPath+servletPath+"/userEdit.xhtml");
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
    }
	
	/**
	 * トップにリダイレクト
	 */
	public void backTop() {
		String contextPath = externalContext.getRequestContextPath();
		String servletPath = externalContext.getRequestServletPath();
        
        try {
			externalContext.
			redirect(contextPath+servletPath+"/index.xhtml");
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
    }


	/**
	 * 編集ゲームダイアログ
	 */
	public void viewEditGame() {
		String contextPath = externalContext.getRequestContextPath();
		String servletPath = externalContext.getRequestServletPath();
        
        try {
			externalContext.
			redirect(contextPath+servletPath+"/liar/editGame.xhtml");
		} catch (IOException e) {
			new OutLogException(e.getMessage());
		}
    }

	public void close() {
		PrimeFaces.current().dialog().closeDynamic("");
	}
}
