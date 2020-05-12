package com.honcari.common;

/**
 * マイページの表示タイプの列挙型.
 * 
 * @author katsuya.fujishima
 *
 */
public enum MyBookTypeEnum {
	/**	書籍一覧 */
	MY_BOOK_LIST(0),
	/** 文学・小説 */
	MY_BOOK_FICTION(1),
	/** 社会・ビジネス */
	MY_BOOK_BUSINESS(2),
	/** 実用・教育 */
	MY_BOOK_EDUCATION(3),
	/** 旅行・地図 */
	MY_BOOK_MAP(4),
	/** 趣味 */
	MY_BOOK_HOBBY(5),
	/** アート・教養・エンタメ */
	MY_BOOK_ENTERTAINMENT(6),
	/** 事典・図鑑・語学・辞書 */
	MY_BOOK_LANGUAGE(7),
	/** こども */
	MY_BOOK_CHILDREN(8),
	/** その他 */
	MY_BOOK_OTHER(9);

	/** key値 */
	private final Integer key;

	/**
	 * コンストラクタ.
	 * 
	 * @param key key値
	 */
	private MyBookTypeEnum(Integer key) {
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
	public static MyBookTypeEnum of(Integer key) {
		for (MyBookTypeEnum page : MyBookTypeEnum.values()) {
			if (page.key == key) {
				return page;
			}
		}
		throw new IndexOutOfBoundsException("The value of enum doesn't exist.");
	}
}