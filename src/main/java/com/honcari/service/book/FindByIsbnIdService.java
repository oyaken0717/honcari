package com.honcari.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.honcari.domain.Book;
import com.honcari.repository.BookRepository;

/**
 * isbnコードより書籍情報を取得するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
public class FindByIsbnIdService {

	@Autowired
	private BookRepository bookRepository;
	
	/**
	 * isbnコードより書籍情報を取得する為のメソッド.
	 * 
	 * @param isbnId isbnコード
	 * @return isbnコードに一致した書籍情報
	 */
	public List<Book> getByIsbnId(String isbnId) {
		return bookRepository.findByIsbnId(isbnId);
	}
}