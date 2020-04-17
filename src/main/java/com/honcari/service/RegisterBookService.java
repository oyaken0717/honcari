package com.honcari.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Book;
import com.honcari.repository.BookRepository;

/**
 * 書籍情報登録に関わるサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class RegisterBookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	/**
	 * booksテーブルに書籍情報を登録するメソッド.
	 * 
	 * @param book 書籍情報
	 */
	public void registerBook(Book book) {
		bookRepository.insert(book);
	}
}