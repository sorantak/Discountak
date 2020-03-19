package com.codepresso.discountak.domain;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class BasketVO {

	private String userEmail;
	private Long prodNo;
	private Date createdAt;
	private ProdVO prod;

}
