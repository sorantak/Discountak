package com.codepresso.discountak.domain;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UserProdListVO {

	private String email;
	private Date createdAt;
	private ProdVO[] prodList;
	
}
