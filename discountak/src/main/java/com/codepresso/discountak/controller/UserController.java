package com.codepresso.discountak.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codepresso.discountak.domain.ResponseData;
import com.codepresso.discountak.domain.UserVO;
import com.codepresso.discountak.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseData signUp(@RequestBody UserVO userVO) throws Exception {
		logger.info("call signUp()");

		int emailResult = userService.checkEmail(userVO);
		boolean ageResult = userService.checkAge(userVO);
		boolean pwResult = userService.checkPw(userVO);

		// 모든 조건 충족할 때 메서드 실행
		if (emailResult == 0 && ageResult == true && pwResult == true) {
			return userService.signUp(userVO);
		} else {
			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseData.setMessage("FAIL");
			responseData.setData(null);
			return responseData;
		}
	}

	@PostMapping("/signin")
	public ResponseData signIn(@RequestBody UserVO userVO) throws Exception {
		logger.info("call signIn()");

		ResponseData responseData = userService.signIn(userVO);
		return responseData;
	}

}
