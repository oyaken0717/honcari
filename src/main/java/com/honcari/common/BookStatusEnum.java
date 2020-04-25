package com.honcari.common;


/**
 * 本の貸出状況を表す列挙型.
 * 
 * @author shumpei
 *
 */
public enum BookStatusEnum {
	
	NOT_RENTABLE("貸出不可", 0), 
	RENTABLE("貸出可", 1), 
	BEFORE_LENDING("貸出承認待ち", 2),
	LENDING("貸出中", 3),
	DELETE("登録解除", 4),
	CANCELED("キャンセル", 9),;

	private final String key;

	private final int value;

	private BookStatusEnum(final String key, final int value) {
		this.key = key;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getKey() {
		return key;
	}

	public static BookStatusEnum of(int value) {
		for (BookStatusEnum orderStatus : BookStatusEnum.values()) {
			if (orderStatus.getValue() == value) {
				return orderStatus;
			}
		}
		throw new IndexOutOfBoundsException("The value of day of week doesn't exist.");
	};

}
