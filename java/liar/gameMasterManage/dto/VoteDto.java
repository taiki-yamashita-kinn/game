package liar.gameMasterManage.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

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

	@Getter
	@Setter
	private String gameId= StringUtils.EMPTY;

	@Getter
	@Setter
	private String userName= StringUtils.EMPTY;

	@Getter
	@Setter
	@NotNull(message="投票を行ってください")
	private String message= StringUtils.EMPTY;

	@Getter
	@Setter
	private String voteDate;

	@Getter
	@Setter
	private Date startDate;

	@Getter
	@Setter
	private Date endDate;

	@Getter
	@Setter
	private String round= StringUtils.EMPTY;

}
