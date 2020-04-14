package com.honcari.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Book;
import com.honcari.repository.BookRepository;

/**
 * 本情報に関するサービスクラス.
 * 
 * @author yamadadai
 *
 */
@Service
@Transactional
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;

	/**
	 * グループIDから１グループの本情報を取得するメソッド.
	 * 
	 * @param groupId グループID
	 * @return 本情報リスト
	 */
	public List<Book> findByGroupId(Integer groupId) {
		return bookRepository.findByGroupId(groupId);
	}
	
	/**
	 * 本IDから本情報を取得するメソッド.
	 * 
	 * @param bookId 本ID
	 * @return 本情報
	 */
	public Book findByBookId(Integer bookId) {
		return bookRepository.findByBookId(bookId);
	}
}
