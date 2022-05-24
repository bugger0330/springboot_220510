package com.springboot.study.domain.user;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface UserRepository {

	public int insertUser(User user);
	public int imgUpdate(String file1);
	public User findUserByUsername(String username);
	public User findOAuth2UserByOAuth2Username(String oAuth2Username);
}
