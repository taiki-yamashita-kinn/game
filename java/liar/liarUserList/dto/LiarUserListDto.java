package liar.liarUserList.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.StreamedContent;

import lombok.Getter;
import lombok.Setter;

public class LiarUserListDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String userId= StringUtils.EMPTY;

	@Getter
	@Setter
	private String userName= StringUtils.EMPTY;

	@Getter
	@Setter
	private StreamedContent userImage;
	
	@Getter
	@Setter
	private String picturePath= StringUtils.EMPTY;

}
