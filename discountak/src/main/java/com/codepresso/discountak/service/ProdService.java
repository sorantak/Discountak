package com.codepresso.discountak.service;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codepresso.discountak.domain.BasketVO;
import com.codepresso.discountak.domain.ProdDetailVO;
import com.codepresso.discountak.domain.ProdVO;
import com.codepresso.discountak.domain.ResponseData;
import com.codepresso.discountak.domain.TokenVO;
import com.codepresso.discountak.domain.UserAndProdVO;
import com.codepresso.discountak.domain.UserProdDetailVO;
import com.codepresso.discountak.domain.UserProdListVO;
import com.codepresso.discountak.repository.BasketDAO;
import com.codepresso.discountak.repository.ProdDAO;
import com.codepresso.discountak.repository.UserDAO;

@Service
public class ProdService {

	private static final Logger logger = LoggerFactory.getLogger(ProdService.class);

	@Autowired
	ProdDAO prodDAO;

	@Autowired
	UserDAO userDAO;

	@Autowired
	BasketDAO basketDAO;

	public ResponseData viewProdWithDetail(String accesstoken, Long no) throws Exception {
		logger.info("call viewProdWithDetail()");

		List<ProdDetailVO> prodDetail = prodDAO.viewProdWithDetail(no);

		UserProdDetailVO[] result = new UserProdDetailVO[prodDetail.size()];
		for (int i = 0; i < result.length; i++) {
			UserProdDetailVO userProdDetail = new UserProdDetailVO();

			UserAndProdVO userAndProd = new UserAndProdVO();
			TokenVO tokenVO = userDAO.viewUserByToken(accesstoken);
			String email = tokenVO.getUserEmail();
			Date createdAt = tokenVO.getCreatedAt();
			userAndProd.setEmail(email);
			userAndProd.setCreatedAt(createdAt);

			ProdVO prod = prodDAO.viewProdByNo(no);
			BasketVO myBasket = basketDAO.viewProdByEmailAndNo(email, no);
			if (myBasket != null) {
				boolean inBasket = true;
				prod.setInBasket(inBasket);
			} else {
				boolean inBasket = false;
				prod.setInBasket(inBasket);
			}
			userAndProd.setProd(prod);
			userProdDetail.setUserAndProd(userAndProd);

			ProdDetailVO detail = new ProdDetailVO();
			Long detailNo = prodDetail.get(i).getNo();
			detail.setNo(detailNo);
			Long prodNo = prodDetail.get(i).getProdNo();
			detail.setProdNo(prodNo);
			String content = prodDetail.get(i).getContent();
			detail.setContent(content);
			String imageURL = prodDetail.get(i).getImageURL();
			detail.setImageURL(imageURL);
			Date detailCreatedAt = prodDetail.get(i).getCreatedAt();
			detail.setCreatedAt(detailCreatedAt);
			userProdDetail.setDetail(detail);

			result[i] = userProdDetail;
		}
		ResponseData responseData = new ResponseData();
		responseData.setCode(HttpStatus.OK);
		responseData.setMessage("SUCCESS");
		responseData.setData(result);
		return responseData;
	}

	public ResponseData viewProdsOnPage(String accesstoken, Long no) throws Exception {
		logger.info("call viewProdsOnPage()");

		long pageNumSet = (no - 1) * 6;

		if (accesstoken != null) {
			UserProdListVO userProdList = new UserProdListVO();
			TokenVO tokenVO = userDAO.viewUserByToken(accesstoken);
			String email = tokenVO.getUserEmail();
			Date createdAt = tokenVO.getCreatedAt();
			userProdList.setEmail(email);
			userProdList.setCreatedAt(createdAt);

			List<ProdVO> prodList = prodDAO.viewProdsOnPage(pageNumSet);
			ProdVO[] prod = new ProdVO[prodList.size()];
			for (int i = 0; i < prod.length; i++) {
				Long prodNo = prodList.get(i).getNo();
				ProdVO prodByNo = prodDAO.viewProdByNo(prodNo);
				BasketVO myBasket = basketDAO.viewProdByEmailAndNo(email, prodNo);
				if (myBasket != null) {
					boolean inBasket = true;
					prodByNo.setInBasket(inBasket);
				} else {
					boolean inBasket = false;
					prodByNo.setInBasket(inBasket);
				}
				prod[i] = prodByNo;
			}
			userProdList.setProdList(prod);

			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.OK);
			responseData.setMessage("SUCCESS");
			responseData.setData(userProdList);
			return responseData;
		} else {
			List<ProdVO> prodList = prodDAO.viewProdsOnPage(pageNumSet);

			ResponseData responseData = new ResponseData();
			responseData.setCode(HttpStatus.OK);
			responseData.setMessage("SUCCESS");
			responseData.setData(prodList);
			return responseData;
		}
	}
}