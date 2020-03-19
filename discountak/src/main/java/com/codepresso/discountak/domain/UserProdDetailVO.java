package com.codepresso.discountak.domain;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UserProdDetailVO {
	
	private UserAndProdVO userAndProd;
	private ProdDetailVO detail;

}
