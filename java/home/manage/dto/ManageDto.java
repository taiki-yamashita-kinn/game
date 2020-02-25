package home.manage.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ManageDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String userId;

	/**
	 * 10文字以内
	 */
	//@Size(max=10)
	@Getter
	@Setter
	private String userName;

	/**
	 * 20文字以内
	 */
	//@Pattern(regexp="\\@{0,1}([0-9a-zA-Z\\_]){0,19}",message="適切なフォーマットで入力してください")
	@Getter
	@Setter
	private String twitterAccount;

}
