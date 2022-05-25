package com.springboot.study.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Value("${file.path}")
	private String filePath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/image/**") // image 뒤에 어떠한 요청이 들어오면
			.addResourceLocations("file:///" + filePath) // /image/ 이 경로를 file:/// + filePath로 바꾸어라
			.setCachePeriod(60*60) //캐시 지속시간 설정(sec) - 캐시에 1시간동안 파일을 저장해두고 요청을 받으면 이미지를 출력해준다
			// 이미지명에 한글이 들어있으면 안된다!!
			
			.resourceChain(true) // 위의 경로를 연결해서 쓰겠다는 뜻(위의 명령어들을 다 실행하고 싶으면 써야한다)
			.addResolver(new PathResourceResolver()); // 중간에서 연결해주는 리졸버가 필요하다(체인을 쓰려면 리졸버가 필요해서)
		
	}
	
}
