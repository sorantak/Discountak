package com.codepresso.discountak.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codepresso.discountak.domain.ResponseData;
import com.codepresso.discountak.domain.TokenVO;
import com.codepresso.discountak.domain.UserVO;
import com.codepresso.discountak.repository.UserDAO;
import com.codepresso.discountak.util.RandomToken;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserDAO userDAO;

	// email 중복 확인
	public int checkEmail(UserVO userVO) throws Exception {
		logger.info("call checkEmail()");

		String email = userVO.getEmail();
		int emailResult = userDAO.checkEmail(email);

		return emailResult;
	}

	// 7세 이하 가입 제한
	public boolean checkAge(UserVO userVO) {
		logger.info("call checkAge()");

		String birth = userVO.getBirth();
		int age = userDAO.convertBirthToAge(birth);
		if (age > 7) {
			return true;
		} else
			return false;
	}

	// 비밀번호 확인
	public boolean checkPw(UserVO userVO) {
		logger.info("call checkPw()");

		logger.info("userVO: " + userVO);

		String password = userVO.getPassword();
		String passwordCheck = userVO.getPasswordCheck();

		if (password.equals(passwordCheck)) {
			return true;
		} else
			return false;
	}

	// 위의 세 method 거친 후 최종 회원가입 method
	public ResponseData signUp(UserVO userVO) throws Exception {
		logger.info("call signUp()");

		userDAO.createUser(userVO);
		String userEmail = userVO.getEmail();
		UserVO userResult = userDAO.findUserByEmail(userEmail);

		ResponseData responseData = new ResponseData();
		responseData.setCode(HttpStatus.OK);
		responseData.setMessage("SUCCESS");
		responseData.setData(userResult);
		return responseData;
	}

	// 로그인
	public ResponseData signIn(UserVO userVO) throws Exception {
		logger.info("call signIn()");

		String email = userVO.getEmail();
		String password = userVO.getPassword();

		UserVO user = new UserVO();
		user.setEmail(email);
		user.setPassword(password);
		int userUserVO = userDAO.findUser(user);
		int userTokenVO = userDAO.checkEmailWithToken(email);

		// 회원이면서 새 로그인 시
		if (userUserVO == 1 && userTokenVO == 0) {
			StringBuffer token = RandomToken.createToken();
			String tokenToString = token.toString();

			TokenVO tokenVO = new TokenVO();
			tokenVO.setUserEmail(email);
			tokenVO.setToken(tokenToString);
			userDAO.createToken(tokenVO);
			TokenVO tokenResult = userDAO.viewUserByToken(tokenToString);

			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.OK);
			responseData.setMessage("SUCCESS");
			responseData.setData(tokenResult);
			return responseData;
		}
		// 회원이면서 재 로그인 시
		else if (userUserVO == 1 && userTokenVO == 1) {
			TokenVO userWithToken = userDAO.viewTokenByEmail(email);

			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.OK);
			responseData.setMessage("SUCCESS");
			responseData.setData(userWithToken);
			return responseData;
		}
		// 회원정보 잘못 입력 시
		else {
			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseData.setMessage("FAIL");
			responseData.setData(null);
			return responseData;
		}
	}

}
