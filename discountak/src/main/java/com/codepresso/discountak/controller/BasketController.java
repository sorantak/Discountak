package com.codepresso.discountak.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codepresso.discountak.domain.ResponseData;
import com.codepresso.discountak.service.BasketService;

@RestController
@RequestMapping("/basket")
public class BasketController {

	@Autowired
	BasketService basketService;

	private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

	@PostMapping("")
	public ResponseData addProd(@CookieValue(value = "accesstoken", required = false) String accesstoken,
			@RequestParam Long prodNo) throws Exception {
		logger.info("call addProd()");
		return basketService.addProd(accesstoken, prodNo);
	}

	@DeleteMapping("")
	public ResponseData removeProd(@CookieValue(value = "accesstoken", required = false) String accesstoken,
			@RequestParam Long prodNo) throws Exception {
		logger.info("call removeProd");
		return basketService.removeProd(accesstoken, prodNo);
	}

	@GetMapping("")
	public ResponseData findAll(@CookieValue(value = "accesstoken", required = false) String accesstoken)
			throws Exception {
		logger.info("call findAll()");
		return basketService.findAll(accesstoken);
	}
}
