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
	
	public BookData getVolumeInfo() {
		return volumeInfo;
	}

	public void setVolumeInfo(BookData volumeInfo) {
		this.volumeInfo = volumeInfo;
	}

	@Override
	public String toString() {
		return "VolumeInfo [volumeInfo=" + volumeInfo + "]";
	}
}
