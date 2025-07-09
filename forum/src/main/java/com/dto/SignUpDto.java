package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
	private String code;
	private String password;
	private String email;
	private String passwordCheck;
	private String nickname;
	private String phoneNumber;
	private String address;
	private String addressDetail;
}
