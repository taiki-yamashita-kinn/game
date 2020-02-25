package home.userEdit.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public class UserEditDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String userId= StringUtils.EMPTY;

	/**
	 * 10文字以内
	 */
	@Size(max=10)
	@NotNull(message="ユーザ名は必須入力です")
	@Getter
	@Setter
	private String userName= StringUtils.EMPTY;

	/**
	 * 10文字以内
	 */
	@Size(max=10)
	@Pattern(regexp="[a-zA-Z0-9]{0,10}",message="半角英数字で入力してください")
	@Getter
	@Setter
	private String password= StringUtils.EMPTY;

	/**
	 * 20文字以内
	 */
	@Pattern(regexp="(\\@([a-zA-Z\\_]){0,19}){0,1}",message="Twitterアカウントは適切なフォーマットで入力してください")
	@Getter
	@Setter
	private String twitterAccount= StringUtils.EMPTY;

	/**
	 * 200文字以内
	 */
	@Size(max=200)
	@Getter
	@Setter
	private String url= StringUtils.EMPTY;

	/**
	 * 100文字以内
	 */
	@Size(max=100)
	@Getter
	@Setter
	private String profile= StringUtils.EMPTY;

	@Getter
	@Setter
	private byte[] userPicture;
	
	@Getter
	@Setter
	private String picturePath= StringUtils.EMPTY;

	@Getter
	@Setter
	private String theme= StringUtils.EMPTY;

	@Getter
	@Setter
	private String userLanguage= StringUtils.EMPTY;


}
