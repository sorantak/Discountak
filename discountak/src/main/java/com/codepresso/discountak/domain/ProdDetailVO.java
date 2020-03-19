package com.codepresso.discountak.domain;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ProdDetailVO {
	
	private Long no;
	private Long prodNo;
	private String content;
	private String imageURL;
	private Date createdAt;

}
