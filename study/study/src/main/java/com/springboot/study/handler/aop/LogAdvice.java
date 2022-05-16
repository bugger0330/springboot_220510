package com.springboot.study.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAdvice {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogAdvice.class);
	
	@Around("within(com.springboot.study..*)")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable { //throws Throwable 필수
		long startAt = System.currentTimeMillis();
		
		Map<String, Object> params = getPrams(pjp);
		
		LOGGER.info("-------Advice Call: {}({}) = {}", pjp.getSignature().getDeclaringTypeName(),
				pjp.getSignature().getName(), params);
		
		Object result = pjp.proceed(); // pjp안에 들어있는 메소드 들을 proceed() 메소드로 실행시켜준다. 
		
		long endAt = System.currentTimeMillis();
		
		LOGGER.info("-------Advice End: {}({}) = {} ({}ms)", pjp.getSignature().getDeclaringTypeName(),
				pjp.getSignature().getName(), result, endAt - startAt);
		
		return result;//pjp.proceed() 이걸 실행시키는게 목적이라서 리턴은 사실 필요없다(void로 해도 됨)
	}
	
	private Map<String, Object> getPrams(ProceedingJoinPoint pjp){
		Map<String, Object> params = new HashMap<String, Object>();
		
		String[] argNames = ((CodeSignature)pjp.getSignature()).getParameterNames(); //getParameterNames을 쓰려면 다운캐스팅을 해야 한다(메소드명을 들고온다)
		Object[] args = pjp.getArgs(); //매개변수의 값 들고온다
		
		for(int i = 0; i < args.length; i++) {
			params.put(argNames[i], args[i]);
		}
		
		return params;
	}
	
}


















