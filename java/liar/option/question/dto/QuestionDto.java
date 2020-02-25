package liar.option.question.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public class QuestionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Getter
	@Setter
	private String id= StringUtils.EMPTY;
	
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
	@Size(max=500)
	private String question= StringUtils.EMPTY;

	@Getter
	@Setter
	private String questionDate= StringUtils.EMPTY;
	
	@Getter
	@Setter
	private String yesNo;
	
	@Getter
	@Setter
	private String round= StringUtils.EMPTY;
}
