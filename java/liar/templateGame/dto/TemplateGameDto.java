package liar.templateGame.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public class TemplateGameDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String id = StringUtils.EMPTY;

	/**
	 * 20文字以内
	 */
	@Getter
	@Setter
	private String gameName= StringUtils.EMPTY;

	/**
	 * ゲームのステータス
	 */
	@Getter
	@Setter
	private String status= StringUtils.EMPTY;

	/**
	 * 10文字以内
	 */
	@Getter
	@Setter
	@Size(max=10)
	private String createUserId= StringUtils.EMPTY;

	/**
	 * 500文字以内
	 */
	@Getter
	@Setter
	@Size(max=500)
	private String descriptionUrl= StringUtils.EMPTY;

	/**
	 * 1ラウンドの時間
	 */
	@Getter
	@Setter
	private String roundjobtime= StringUtils.EMPTY;

	/**
	 * 発言禁止時間
	 */
	@Getter
	@Setter
	private String banJobTime= StringUtils.EMPTY;

	/**
	 * ゲームの所要時間
	 */
	@Getter
	@Setter
	private String gameTime= StringUtils.EMPTY;
}
