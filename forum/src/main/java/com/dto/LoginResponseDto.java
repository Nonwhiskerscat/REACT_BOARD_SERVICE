package com.dto;

import com.entity.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
	private String token;
	private int exprTime;
	private UserVO user;
}