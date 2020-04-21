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
	/**	文学・小説 */
	MY_BOOK_FICTION(6),
	/** 社会・ビジネス	*/
	MY_BOOK_BUSINESS(7),
	/**	実用・教育 */
	MY_BOOK_EDUCATION(8),
	/** 旅行・地図 */
	MY_BOOK_MAP(9),
	/**	趣味 */
	MY_BOOK_HOBBY(10),
	/**	アート・教養・エンタメ */
	MY_BOOK_ENTERTAINMENT(11),
	/** 事典・図鑑・語学・辞書 */
	MY_BOOK_LANGUAGE(12),
	/** こども */
	MY_BOOK_CHILDREN(13),
	/** その他 */
	MY_BOOK_OTHER(14)
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