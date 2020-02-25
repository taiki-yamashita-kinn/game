package home.userSearch.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public class UserSearchDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 10文字以内
	 */
	@Size(max=10)
	@Getter
	@Setter
	private String userName;

	/**
	 * 20文字以内
	 */
	@Pattern(regexp="\\@{0,1}([0-9a-zA-Z\\_]){0,19}",message="適切なフォーマットで入力してください")
	@Getter
	@Setter
	private String twitterAccount;

	@Getter
	@Setter
	private String creativeParameterFrom;

	@Getter
	@Setter
	private String creativeParameterTo;

	@Getter
	@Setter
	private String logicParameterFrom;

	@Getter
	@Setter
	private String logicParameterTo;

	@Getter
	@Setter
	private String volunteerParameterFrom;

	@Getter
	@Setter
	private String volunteerParameterTo;

	@Getter
	@Setter
	private String individualParameterFrom;

	@Getter
	@Setter
	private String individualParameterTo;

	@Getter
	@Setter
	private String favoriteParameterFrom;

	@Getter
	@Setter
	private String favoriteParameterTo;

	@Getter
	@Setter
	@Pattern(regexp="[0-9]{0,7}",message="7桁以内の数字で入力してください")
	private String evaluationFrom;

	@Getter
	@Setter
	@Pattern(regexp="[0-9]{0,7}",message="7桁以内の数字で入力してください")
	private String evaluationTo;

	@Getter
	@Setter
	@Pattern(regexp="[0-9]{0,7}",message="7桁以内の数字で入力してください")
	private String battleCountFrom;

	@Getter
	@Setter
	@Pattern(regexp="[0-9]{0,7}",message="7桁以内の数字で入力してください")
	private String battleCountTo;

}

