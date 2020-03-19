package com.codepresso.discountak.service;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codepresso.discountak.domain.BasketVO;
import com.codepresso.discountak.domain.ProdVO;
import com.codepresso.discountak.domain.ResponseData;
import com.codepresso.discountak.domain.TokenVO;
import com.codepresso.discountak.domain.UserAndProdVO;
import com.codepresso.discountak.repository.BasketDAO;
import com.codepresso.discountak.repository.ProdDAO;
import com.codepresso.discountak.repository.UserDAO;

@Service
public class BasketService {

	@Autowired
	UserDAO userDAO;

	@Autowired
	BasketDAO basketDAO;

	@Autowired
	ProdDAO prodDAO;

	private static final Logger logger = LoggerFactory.getLogger(BasketService.class);

	// 장바구니 추가
	public ResponseData addProd(String accesstoken, Long prodNo) throws Exception {
		logger.info("call addProd()");
		try {
			TokenVO tokenVO = userDAO.viewUserByToken(accesstoken);
			String email = tokenVO.getUserEmail();

			basketDAO.addProdInBasket(email, prodNo);
			BasketVO addProdResult = basketDAO.viewProdByEmailAndNo(email, prodNo);
			addProdResult.setProd(prodDAO.viewProdByNo(prodNo));

			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.OK);
			responseData.setMessage("SUCCESS");
			responseData.setData(addProdResult);
			return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseData.setMessage("FAIL");
			responseData.setData(null);
			return responseData;
		}

	}

	// 장바구니 삭제
	public ResponseData removeProd(String accesstoken, Long prodNo) {
		logger.info("call removeProd()");
		try {
			TokenVO tokenVO = userDAO.viewUserByToken(accesstoken);
			String email = tokenVO.getUserEmail();

			basketDAO.removeProdFromBasket(email, prodNo);

			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.OK);
			responseData.setMessage("SUCCESS");
			responseData.setData(null);
			return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseData.setMessage("FAIL");
			responseData.setData(null);
			return responseData;
		}
	}

	// 장바구니 목록
	public ResponseData findAll(String accesstoken) {
		logger.info("call findAll()");
		try {
			TokenVO tokenVO = userDAO.viewUserByToken(accesstoken);
			String email = tokenVO.getUserEmail();

			UserAndProdVO userAndProdVO = new UserAndProdVO();
			userAndProdVO.setEmail(email);

			List<BasketVO> myBasket = basketDAO.viewAllByEmail(email);
			UserAndProdVO[] prods = new UserAndProdVO[myBasket.size()];
			for (int i = 0; i < prods.length; i++) {
				UserAndProdVO userNProdVO = new UserAndProdVO();

				Long prodNo = myBasket.get(i).getProdNo();
				ProdVO prodVO = new ProdVO();
				prodVO = prodDAO.viewProdByNo(prodNo);
				prodVO.setInBasket(true);
				userNProdVO.setProd(prodVO);

				String userEmail = myBasket.get(i).getUserEmail();
				userNProdVO.setEmail(userEmail);
				Date createdAt = myBasket.get(i).getCreatedAt();
				userNProdVO.setCreatedAt(createdAt);

				prods[i] = userNProdVO;
			}

			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.OK);
			responseData.setMessage("SUCCESS");
			responseData.setData(prods);
			return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseData.setMessage("FAIL");
			responseData.setData(null);
			return responseData;
		}
	}

}
