package com.codepresso.discountak.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.codepresso.discountak.domain.BasketVO;

@Repository
public class BasketDAO {

	private static final Logger logger = LoggerFactory.getLogger(BasketDAO.class);

	@Autowired
	private SqlSession sqlSession;

	private static final String Namespace = "mybatis.mappers.basket";

	public List<BasketVO> findProdsByEmail(String userEmail) throws Exception {
		logger.info("call findProdsByEmail()");
		return sqlSession.selectList(Namespace + ".findProdsByEmail", userEmail);
	}

	public BasketVO viewProdByEmailAndNo(String userEmail, Long no) throws Exception {
		logger.info("call viewProdByEmailAndNo()");

		// 가져온 parameter는 여러 개지만 sqlSession.selectOne을 하기 위해서는 하나의 parameter밖에 넣을 수 없다.
		// 이를 위해 새로운 객체에 두 parameter를 넣어준다.
		// http://www.devkuma.com/books/pages/741
		BasketVO params = new BasketVO();
		params.setUserEmail(userEmail);
		params.setProdNo(no);

		return sqlSession.selectOne(Namespace + ".viewProdByEmailAndNo", params);
	}

	public int addProdInBasket(String userEmail, Long prodNo) throws Exception {
		logger.info("call addProdInBasket()");

		BasketVO params = new BasketVO();
		params.setUserEmail(userEmail);
		params.setProdNo(prodNo);

		return sqlSession.insert(Namespace + ".addProdInBasket", params);
	}

	public int removeProdFromBasket(String userEmail, Long prodNo) {
		logger.info("call removeProdFromBasket()");

		BasketVO params = new BasketVO();
		params.setUserEmail(userEmail);
		params.setProdNo(prodNo);

		return sqlSession.delete(Namespace + ".removeProdFromBasket", params);
	}

	public List<BasketVO> viewAllByEmail(String userEmail) {
		logger.info("call viewAllByEmail()");
		return sqlSession.selectList(Namespace + ".viewAllByEmail", userEmail);
	}
}
