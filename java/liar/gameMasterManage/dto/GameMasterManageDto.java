package liar.gameMasterManage.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public class GameMasterManageDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String userId= StringUtils.EMPTY;

	@Getter
	@Setter
	private String gameId= StringUtils.EMPTY;

	@Getter
	@Setter
	private String roomId= StringUtils.EMPTY;

	/**
	 * 10文字以内
	 */
	@Getter
	@Setter
	private String userName= StringUtils.EMPTY;

	@Getter
	@Setter
	private String message= StringUtils.EMPTY;

	@Getter
	@Setter
	private String speakDate= StringUtils.EMPTY;

	@Getter
	@Setter
	private String round= StringUtils.EMPTY;

}
