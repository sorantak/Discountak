package com.codepresso.discountak.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.codepresso.discountak.domain.ProdVO;
import com.codepresso.discountak.domain.ProdDetailVO;

@Repository
public class ProdDAO {

	private static final Logger logger = LoggerFactory.getLogger(ProdDAO.class);

	@Autowired
	private SqlSession sqlSession;

	private static final String Namespace = "mybatis.mappers.prod";

	public ProdVO viewProdByNo(Long no) throws Exception {
		logger.info("call viewProdByNo()");
		return sqlSession.selectOne(Namespace + ".viewProdByNo", no);
	}

	public List<ProdDetailVO> viewProdWithDetail(Long prodNo) throws Exception {
		logger.info("call viewProdWithDetail()");
		return sqlSession.selectList(Namespace + ".viewProdWithDetail", prodNo);
	}

	public ProdDetailVO viewDetailByNoProdNo(Long detailNo, Long prodNo) {
		logger.info("call viewDetailByNoProdNo()");
		ProdDetailVO params = new ProdDetailVO();
		params.setNo(detailNo);
		params.setProdNo(prodNo);
		return sqlSession.selectOne(Namespace + ".viewDetailByNoProdNo", detailNo);
	}

	public List<ProdVO> viewProdsOnPage(Long pageNo) {
		logger.info("call viewProdsOnPage()");
		return sqlSession.selectList(Namespace + ".viewProdsOnPage", pageNo);
	}

}
