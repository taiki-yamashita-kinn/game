package home.manage.mapper;

import java.util.Map;

public interface PostInfoMapper {
	/**
	 * お知らせを登録する
	 * @param condition
	 */
	void registerInfo(Map<String, Object> condition);


	/**
	 * ヘルプを登録する
	 * @param condition
	 */
	void registerHelp(Map<String, Object> condition);

}
