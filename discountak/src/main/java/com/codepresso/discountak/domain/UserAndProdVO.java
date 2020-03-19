package com.codepresso.discountak.domain;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UserAndProdVO {
	
	private String email;
	private Date createdAt;
	private ProdVO prod;
	
}
// 삭제해도 무방