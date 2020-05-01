package com.honcari.common;

public enum RentalStatusEnum {
	
	/**
	 * 本の貸出プロセスの状況を表す列挙型.
	 * 
	 * @author yamaseki
	 *
	 */
	WAIT_APPROVAL("承認待ち", 0), 
	APPROVED("承認済み", 1), 
	WAIT_RETURNING("返却待ち", 2),
	RETURNED("返却済み", 3),
	WAIT_EXTEND("延長承認待ち", 4),
	CANCELED("申請キャンセル", 8),
	REJECTED("承認拒否", 9),;
	
	private final String key;
	private final int value;
	
	private RentalStatusEnum(String key, int value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public int getValue() {
		return value;
	}
	
	public static RentalStatusEnum of(int value) {
		for (RentalStatusEnum orderStatus : RentalStatusEnum.values()) {
			if (orderStatus.getValue() == value) {
				return orderStatus;
			}
		}
		throw new IndexOutOfBoundsException("The value of day of week doesn't exist.");
	};

}
