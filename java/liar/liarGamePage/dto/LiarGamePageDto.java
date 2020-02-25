package liar.liarGamePage.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public class LiarGamePageDto implements Serializable {

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

	@Getter
	@Setter
	private String roomName= StringUtils.EMPTY;

	@Getter
	@Setter
	private String userName= StringUtils.EMPTY;

}
