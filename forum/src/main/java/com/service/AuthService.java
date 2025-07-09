package com.service;

import com.dto.LoginDto;
import com.dto.LoginResponseDto;
import com.dto.ResponseDto;
import com.dto.SignUpDto;
import com.entity.UserVO;

import com.repository.UserRepository;
import com.security.LoginVO;
import com.security.TokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AuthService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	TokenProvider tokenProvider;

	public ResponseDto<?> signUp(SignUpDto dto) throws Exception {
		String code = dto.getCode();
		String password = dto.getPassword();
		String passwordCheck = dto.getPasswordCheck();

		// email 중복 확인
		if (userRepository.existsByCode(dto.getCode())) {
            return ResponseDto.setFailed("이미 존재하는 코드입니다.");
        }

		if (!password.equals(passwordCheck)) {
			return ResponseDto.setFailed("패스워드가 일치하지 않습니다!");
		}

		UserVO userVO = new UserVO(dto); // UserEntity 생성
		userRepository.save(userVO);

		return ResponseDto.setSuccess("SignUp Success!", null);
	}

	@Transactional(readOnly = true)
	public ResponseDto<LoginResponseDto> login(LoginDto dto, HttpServletRequest request) throws Exception {
		
		String code = dto.getCode();
		String password = dto.getPassword();
		boolean existed = userRepository.existsByCodeAndPassword(code, password);

		if (!existed) {
			return ResponseDto.setFailed("IP/PW를 확인해 주십시오.");
		}

		// 값이 존재하면
		UserVO userVO = userRepository.findByCode(code);
		if (userVO == null) {
			return ResponseDto.setFailed("유저 정보를 불러오는 데에 오류가 발생했습니다.");
		}
				
		LoginVO loginVO = LoginVO.builder().code(userVO.getCode()).nickname(userVO.getNickname())
				.phoneNumber(userVO.getPhoneNumber()).address(userVO.getAddress()).build();

		String token = tokenProvider.createJwt(code);
		int exprTime = 3600000; // 한 시간

		HttpSession session = request.getSession(true); // 없으면 새로 생성
		session.setAttribute("code", code);
		session.setAttribute("token", token);
        session.setAttribute("loginVO", loginVO);
		session.setMaxInactiveInterval(exprTime / 1000); // 1시간

		LoginResponseDto body = new LoginResponseDto(token, exprTime, userVO);
		return ResponseDto.setSuccess("Login Success", body);
	}
}