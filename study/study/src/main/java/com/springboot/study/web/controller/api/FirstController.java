package com.springboot.study.web.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
	
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	/*
	 * 덧셈 문제
	 * 요청 리소스는 add-덧셈, sub-뺄셈, mul(곱셈), div(나눗셈)
	 * 파라미터는 a, b라는 변수 사용
	 * 
	 * div 파라미터가 둘 중 하나라도 0이면 "0으로 계산할 수 없습니다." 출력
	 * 
	 * 
	 */
	
	@GetMapping("/add")
	public int add(int a, int b) {
		int c = a + b;
		return c;
	}
	@GetMapping("/sub")
	public int sub(int a, int b) {
		int c = a - b;
		return c;
	}
	@GetMapping("/mul")
	public int mul(int a, int b) {
		int c = a * b;
		return c;
	}
	@GetMapping("/div")
	public String div(int a, int b) {
		if(a == 0 || b == 0) {
			String st = "0으로 계산할 수 없습니다";
			return st;
		}
		double c = a / b;
		
		return Double.toString(c);
	}
	
	/*
	 * 만약 밸류가 여러개가 날아올 경우
	 * 배열(list)로 매개변수를 받아서 쓸 수 있다.
	 */
	
	
	@GetMapping("/add2")
	public int add2(@RequestParam("v") List<Integer> a) {
		int sum = 0;
		for(int i = 0; i < a.size(); i++) {
			sum += a.get(i);
		}
		return sum;
	}
	
	@GetMapping("/add3")
	public int add3(@RequestParam("v") List<Integer> a) {
		int sum = 0;
		for(int i : a) {
			sum += i;
		}
		return sum;
	}
	
}
