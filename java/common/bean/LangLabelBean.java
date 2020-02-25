package common.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import common.dto.LoginInfo;
import common.service.interfaces.common.CommonService;

//言語情報を扱うクラス
@ApplicationScoped
@Named
public class LangLabelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private LoginInfo loginInfo;

	/**
	 * DBからマップのリストで取得
	 */
	private List<Map<String,Object>> labeList;

	@Inject
	private CommonService commonService;

	//日本語のラベルマップ
	private Map<String,Object> labelJapanMap;

	//英語のラベルマップ
	private Map<String,Object> labelEnglishMap;


	@PostConstruct
	public void init() {
		//多言語ラベルをサーバー起動時に取得
		labeList = commonService.selectLabel();

		labelJapanMap = new HashMap<>();

		labelEnglishMap = new HashMap<>();

		//ラベル一覧をそれぞれの言語マップに入れる
		for(Map<String,Object> labelData :labeList) {
			labelJapanMap.put(labelData.get("labelId").toString(), labelData.get("labelJapan").toString());
			labelEnglishMap.put(labelData.get("labelId").toString(), labelData.get("labelEnglish").toString());
		}

	}

	/**
	 * 多言語情報をマップから取得するメソッド
	 * @param key ラベルのキー
	 * @return
	 */
	public String getLabel(String key) {

		if(labelEnglishMap.get(key) == null || labelJapanMap.get(key) == null) {
			return "Message Empty";
		}

		if(loginInfo.getUserLanguage().equals("1")) {
			return labelEnglishMap.get(key).toString();
		}else {
			return labelJapanMap.get(key).toString();
		}

	}
}
