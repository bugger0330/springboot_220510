package com.springboot.study.service.user;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.study.config.auth.PrincipalDetails;
import com.springboot.study.domain.user.User;
import com.springboot.study.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	@Value("${file.path}") // @service가 ioc에 등록될떄 @value가 있으면 String filepath라는 이름의 변수가 생성되는데, 그 안의 값이 "${file.path}" 가 된다
	private String filePath;

	private final UserRepository userRepository;

	@Override
	public boolean updateProfileImg(MultipartFile file, PrincipalDetails principalDetails) {
		if(file != null) {
			String originFileName = file.getOriginalFilename();
			String tempFileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + originFileName;
			Path uploadPath = Paths.get(filePath, "profile/" + tempFileName);
			
			File f = new File(filePath + "profile");//.yml에서 path: C:/junil/kangmin/workspace/git/SpringBoot/upload/ 맨뒤에 /를 붙이면 filePath + "profile" 에서 filePath 뒤에 슬러시를 붙이지 않아도 된다.
			if(!f.exists()) { // f 의 해당 경로가 존재하면 true, false 일떄 경로를 만들어야 하니까 ! 붙임
				f.mkdirs();
			}
			try {
				Files.write(uploadPath, file.getBytes());
				User user = principalDetails.getUser();
				user.setProfile_img_url(tempFileName);
				
				return userRepository.updateProfileImg(user) > 0 ? true : false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //tempFileName 에 내용물이 없는 빈 파일을 만들고 나서 그 안에 내용을 넣어라
		}
		return false;
	}
	


}
