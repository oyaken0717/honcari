package com.honcari.service.book;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.honcari.domain.GoogleBooks;

/**
 * GoogleBookApiから書籍情報を取得するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class GoogleBookApiService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	private static final String URL = "https://www.googleapis.com/books/v1/volumes?q=";
	
	
	/**
	 * GoogleBookAPIから書籍情報を取得するメソッド(15件).
	 * 
	 * @param name 検索ワード
	 * @param pageNumber startIndexを指定する
	 * @return 検索ワードに一致した書籍情報
	 */
	public GoogleBooks getBook(String name, Integer pageNumber) {
		String searchUrl = null;
		GoogleBooks googleBooks = new GoogleBooks();
		try {
			searchUrl = URL + name + "&maxResults=15&startIndex=" + pageNumber * 17;
			googleBooks = restTemplate.getForObject(searchUrl, GoogleBooks.class);
		}catch(Exception e){
			e.printStackTrace();
			//TODO 例外処理を記述する
		}
		return googleBooks;
	}
}