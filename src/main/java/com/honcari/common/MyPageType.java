package com.honcari.common;

/**
 * マイページの表示タイプの列挙型.
 * 
 * @author katsuya.fujishima
 *
 */
public enum MyPageType {
	/** プロフィール */
	PROFILE(1),
	/** マイブック */
	MY_BOOK(2),
	/** レンタル管理 */
	LENTAL_MANAGEMENT(3),
	/** レンタル履歴 */
	LENTAL_HISTORY(4),
	/** プロフィール編集 */
	EDIT_PROFILE(5),
	;
	
	/** key値 */
	private final Integer key;

	/**
	 * コンストラクタ.
	 * 
	 * @param key key値
	 */
	private MyPageType(Integer key) {
		this.key = key;
	}
	
	public Integer getKey() {
		return key;
	}
	
	/**
	 * key値からEnumを取得するメソッド.
	 * 
	 * @param key key値
	 * @return 渡されたkeyを含むEnum
	 */
	public static MyPageType of(Integer key) {
		for(MyPageType page: MyPageType.values()) {
			if(page.key == key) {
				return page;
			}
		}
		throw new IndexOutOfBoundsException("The value of enum doesn't exist.");
	}
}
