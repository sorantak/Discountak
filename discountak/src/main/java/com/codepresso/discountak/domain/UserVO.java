package com.codepresso.discountak.domain;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UserVO {

	private String email;
	private String name;
	private String birth;
	private String password;
	private String passwordCheck;
	private String gender;
	private Date createdAt;

}
