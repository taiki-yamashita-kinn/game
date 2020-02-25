package liar.liarChat.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public class ImageDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String userId= StringUtils.EMPTY;

	@Getter
	@Setter
	private byte[] userPicture;

}
