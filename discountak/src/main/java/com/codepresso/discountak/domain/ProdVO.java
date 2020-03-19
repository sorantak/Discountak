package com.codepresso.discountak.domain;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ProdVO {
	
	private Long no;
	private String name;
	private String thumbnailURL;
	private Long originPrice;
	private Long discPrice;
	private String description;
	private Date createdAt;
	private boolean inBasket;

}
