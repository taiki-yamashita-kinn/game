package common.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.StreamedContent;

import lombok.Getter;
import lombok.Setter;

public class MessageDto implements Serializable{

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
	private String roomId= StringUtils.EMPTY;

	@Getter
	@Setter
	private String userName= StringUtils.EMPTY;

	@Getter
	@Setter
	private String villageId= StringUtils.EMPTY;

	@Getter
	@Setter
	private String gameId= StringUtils.EMPTY;

	@Getter
	@Setter
	@Size(max=200)
	private String message= StringUtils.EMPTY;

	@Getter
	@Setter
	private String speakDate= StringUtils.EMPTY;

	//chatで表示する際のstyleClassのCSS変数
	@Getter
	@Setter
	private String styleClass= StringUtils.EMPTY;

	//ユーザー画像
	@Getter
	@Setter
	private String picturePath= StringUtils.EMPTY;
	
	//ユーザー画像
	@Getter
	@Setter
	private StreamedContent userImage;

	//ゲームマスターの発言かどうかのフラグ
	@Getter
	@Setter
	private String gameMasterFlag= StringUtils.EMPTY;

}
