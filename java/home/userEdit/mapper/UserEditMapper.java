package home.userEdit.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import home.userEdit.dto.UserEditDto;

public interface UserEditMapper {
	/**
	 * 更新するユーザーを取得する
	 * @param userId
	 * @return
	 */
	UserEditDto selectUser(@Param("userId") String userId);

	/**
	 * ユーザーを更新する
	 * @param condition
	 */
	void updateUser(Map<String, Object> condition);

	/**
	 * ユーザーのURLを消去
	 * @param userId
	 * @return
	 */
	void deleteUserUrl(@Param("userId") String userId);

	/**
	 * ユーザーのURLを挿入
	 * @param userId
	 * @return
	 */
	void insertUserUrl(@Param("userId") String userId,@Param("userUrl") String userUrl);

}
