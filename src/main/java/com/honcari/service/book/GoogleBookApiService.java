package com.honcari.service.book;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.honcari.domain.GoogleBooks;

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
	
	public GoogleBooks getBook(String name) {
		String searchUrl = null;
		GoogleBooks googleBooks = new GoogleBooks();
		try {
			searchUrl = URL + name;
			googleBooks = restTemplate.getForObject(searchUrl, GoogleBooks.class);
			googleBooks.getItems().get(0).getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier();
		}catch(Exception e){
			e.printStackTrace();
		}
		return googleBooks;
	}
}