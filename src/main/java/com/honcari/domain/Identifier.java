package com.honcari.domain;

/**
 * ISBNコード用のドメイン
 * 
 * @author hatakeyamakouta
 *
 */
public class Identifier {

	/**	ISBNコード */
	private String identifier;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return "Identifier [identifier=" + identifier + "]";
	}
}
