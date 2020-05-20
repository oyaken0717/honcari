package com.honcari.domain;

/**
 * GoogleBookApi用の書籍ドメイン
 * 
 * @author hatakeyamakouta
 *
 */
public class VolumeInfo {

	/**	GoogleBookApiか取得したデータが入る */
	private BookData volumeInfo;
	
	/** googleBookのid */
	private String id;

	public BookData getVolumeInfo() {
		return volumeInfo;
	}

	public void setVolumeInfo(BookData volumeInfo) {
		this.volumeInfo = volumeInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "VolumeInfo [volumeInfo=" + volumeInfo + ", id=" + id + "]";
	}
}