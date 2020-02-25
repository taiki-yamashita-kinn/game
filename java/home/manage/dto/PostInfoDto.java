package home.manage.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

public class PostInfoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * URLのリンクの名前
	 */
	@Getter
	@Setter
	@NotNull(message="リンクの名前は必須入力です")
	private String text;

	/**
	 * 遷移先url
	 */
	@Getter
	@Setter
	@NotNull(message="URLは必須入力です")
	@Pattern(regexp="((https\\:\\/\\/docs.google.com\\/spreadsheets\\/).*){0,1}|"
			+ "((https\\:\\/\\/docs.google.com\\/document\\/).*){0,1}|"
			+ "((https\\:\\/\\/docs.google.com\\/presentation\\/).*){0,1}"
	,message="適切なフォーマットでGoogleURLを入力してください")
	private String url;

	/**
	 * URLtitle
	 */
	@Getter
	@Setter
	private String title;

	/**
	 * お知らせかヘルプかなどのフラグ
	 */
	@Getter
	@Setter
	private int postFlag;

}
