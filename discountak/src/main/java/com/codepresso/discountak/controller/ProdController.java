package com.codepresso.discountak.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codepresso.discountak.domain.ResponseData;
import com.codepresso.discountak.service.ProdService;

@RestController
@RequestMapping("/prod")
public class ProdController {

	private static final Logger logger = LoggerFactory.getLogger(ProdController.class);

	@Autowired
	ProdService prodService;

	@GetMapping("/{no}")
	public ResponseData viewProdWithDetail(@CookieValue(value = "accesstoken", required = false) String accesstoken,
			@PathVariable("no") Long no) throws Exception {
		logger.info("call viewProdWithDetail()");		
		return prodService.viewProdWithDetail(accesstoken, no);
	}

	@GetMapping("/page/{no}")
	public ResponseData viewProdsOnPage(@CookieValue(value = "accesstoken", required = false) String accesstoken,
			@PathVariable("no") Long no) throws Exception {
		logger.info("call viewProdsOnPage()");		
		return prodService.viewProdsOnPage(accesstoken, no);
	}
	
}
