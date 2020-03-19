package com.codepresso.discountak.domain;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class TokenVO {

	private String token;
	private String userEmail;
	private Date createdAt;

}
