package com.honcari.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.honcari.domain.User;

@Repository
public class UserRepository {

	private static final RowMapper<User> User_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("user_id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setImagePath("image_path");
		user.setProfile("profile");
		return user;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	public void insert(User user) {
		String sql = "INSERT INTO Users(name,email,password,image_path,profile)VALUES(:name,:email,:password,:imagePath,:profile)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
	}

	public User findByEmail(String email) {
		String sql = "SELECT user_id,name,email,password,image_path,profile from Users where email = :email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, User_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);

	}
}
