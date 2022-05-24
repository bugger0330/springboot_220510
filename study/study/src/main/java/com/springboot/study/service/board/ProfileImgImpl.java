package com.springboot.study.service.board;

import org.springframework.stereotype.Service;

import com.springboot.study.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileImgImpl implements ProfileImg {

	private UserRepository userRepository;
	
	@Override
	public boolean changeImg(String file1) {
		int result = userRepository.imgUpdate(file1);
		return result != 0;
	}

}
