package com.honcari.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.honcari.domain.Category;

/**
 * categoryテーブルを操作するリポジトリ.
 * 
 * @author hatakeyamakouta
 *
 */
@Repository
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	// カテゴリのローマッパー
	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, i) ->{
		Category category = new Category();
		category.setId(rs.getInt("category_id"));
		category.setName(rs.getString("name"));
		return category;
	};
	
	/**
	 * カテゴリ一覧を取得するメソッド.
	 * 
	 * @return カテゴリ一覧
	 */
	public List<Category> findAll(){
		String sql = "SELECT category_id, name FROM category;";
		return template.query(sql, CATEGORY_ROW_MAPPER);
	}
}