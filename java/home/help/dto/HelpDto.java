package home.help.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public class HelpDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String id= StringUtils.EMPTY;

	@Getter
	@Setter
	private String url= StringUtils.EMPTY;

	@Getter
	@Setter
	private String title= StringUtils.EMPTY;

	@Getter
	@Setter
	private String text= StringUtils.EMPTY;

}
