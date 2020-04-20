package com.honcari.common;

/**
 * マイページの表示タイプの列挙型.
 * 
 * @author katsuya.fujishima
 *
 */
public enum MyPageType {
	/** 商品一覧 */
	PROFILE(1),
	/** 商品詳細 */
	MY_BOOK(2),
	/** 商品編集 */
	LENTAL_MANAGEMENT(3),
	/** 商品登録 */
	LENTAL_HISTORY(4),
	/** カテゴリー一覧 */
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
