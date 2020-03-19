package com.codepresso.discountak.repository;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.codepresso.discountak.domain.ProdVO;
import com.codepresso.discountak.domain.TokenVO;
import com.codepresso.discountak.domain.UserAndProdVO;
import com.codepresso.discountak.domain.UserVO;

@Repository
public class UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

	@Autowired
	private SqlSession sqlSession;

	private static final String Namespace = "mybatis.mappers.user";

	// email 중복 확인 query
	public int checkEmail(String email) throws Exception {
		logger.info("call checkEmail()");
		return sqlSession.selectOne(Namespace + ".checkEmail", email);
	}

	// 7세 이하 가입 제한 query
	public int convertBirthToAge(String birth) {
		logger.info("call convertBirthToAge()");
		return sqlSession.selectOne(Namespace + ".convertBirthToAge", birth);
	}

	// 회원가입 query 1
	public int createUser(UserVO userVO) throws Exception {
		logger.info("call createUser()");
		return sqlSession.insert(Namespace + ".createUser", userVO);
	}

	// 회원가입 query 2
	public UserVO findUserByEmail(String email) {
		logger.info("call findUserByEmail()");
		return sqlSession.selectOne(Namespace + ".findUserByEmail", email);
	}

	// 회원인지 확인
	public int findUser(UserVO user) {
		logger.info("call findUser()");
		return sqlSession.selectOne(Namespace + ".findUser", user);
	}

	// 새 로그인인지 확인
	public int checkEmailWithToken(String email) {
		logger.info("call checkEmailWithToken()");
		return sqlSession.selectOne(Namespace + ".checkEmailWithToken", email);
	}

	// 새 로그인: 토큰 생성
	public int createToken(TokenVO tokenVO) {
		logger.info("call createToken()");
		return sqlSession.insert(Namespace + ".createToken", tokenVO);

	}

	// 새 로그인
	public TokenVO viewUserByToken(String token) {
		logger.info("call viewUserByToken()");
		return sqlSession.selectOne(Namespace + ".viewUserByToken", token);
	}

	// 재 로그인
	public TokenVO viewTokenByEmail(String email) {
		logger.info("call viewTokenByEmail()");
		return sqlSession.selectOne(Namespace + ".viewTokenByEmail", email);
	}
	
	// 내 장바구니에 담긴 상품 하나 조회
	public UserAndProdVO viewProdByEmailAndNo(String email, Long no) {
		logger.info("call viewProdByEmailAndNo()");
		
		UserAndProdVO params = new UserAndProdVO();
		ProdVO prod = new ProdVO();
		prod.setNo(no);

		params.setEmail(email);
		params.setProd(prod);

		return sqlSession.selectOne(Namespace + ".viewProdByEmailAndNo", params);
	}
	

}
