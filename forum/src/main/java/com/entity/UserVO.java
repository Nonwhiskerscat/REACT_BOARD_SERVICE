package com.entity;

import com.dto.SignUpDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "User")
@Table(name = "UserTbl")
public class UserVO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
	
	@Column(unique = true)
	private String code;
	private String password;
	private String email;
	private String nickname;
	private String phoneNumber;
	private String address;

	public UserVO(SignUpDto dto) {
		this.code = dto.getCode();
		this.password = dto.getPassword();
		this.email = dto.getEmail();
		this.nickname = dto.getNickname();
		this.phoneNumber = dto.getPhoneNumber();
		this.address = dto.getAddress() + " " + dto.getAddressDetail();
	}
}