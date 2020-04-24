package com.honcari.service.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.honcari.domain.LoginUser;
import com.honcari.domain.User;
import com.honcari.repository.UserRepository;

/**
 * ログイン後のユーザーに権限情報を付与するサービスクラス.
 * 
 * @author yamaseki
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	/** DBから情報を得るためのリポジトリ */
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HttpSession session;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		
		if (user == null) {
			System.out.println("ログイン失敗");
			throw new UsernameNotFoundException("そのEmailは登録されていません。");
		}
		System.out.println("ログイン成功:"+user);
		// 権限付与の例
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザ権限付与
		
		//ログイン成功時にuser情報をsessionスコープに格納する
		session.setAttribute("userId",user.getId());
			
		return new LoginUser(user,authorityList);
	}
}
