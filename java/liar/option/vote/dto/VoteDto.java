package liar.option.vote.dto;

import java.io.Serializable;


import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public class VoteDto implements Serializable{

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
	@Getter
	@Setter
	private String gameId= StringUtils.EMPTY;

	@Getter
	@Setter
	private String vote= StringUtils.EMPTY;

	@Getter
	@Setter
	private String voteDate= StringUtils.EMPTY;

	@Getter
	@Setter
	private String round= StringUtils.EMPTY;
}
