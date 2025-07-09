package com.controller;

import com.dto.*;
import com.security.LoginVO;
import com.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthService authService;

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ResponseDto<?> signUp(@ModelAttribute SignUpDto dto) throws Exception {
		System.out.println(dto.toString());
		ResponseDto<?> result = authService.signUp(dto);
		return result;
	}

    @RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseDto<LoginResponseDto> login(@ModelAttribute LoginDto dto, HttpServletRequest request) throws Exception {
		ResponseDto<LoginResponseDto> result = authService.login(dto, request);
		return result;
	}

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<Void> logout(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		if (session != null) session.invalidate();   // 세션 완전 삭제
		return ResponseEntity.ok().build();
	}

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<LoginVO> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoginVO loginVO = session == null ? null : (LoginVO) session.getAttribute("loginVO");
        if (loginVO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(loginVO);
    }

    @RequestMapping(value = "/session/validate", method = RequestMethod.GET)
    public ResponseEntity<?> validateSession(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("code") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }
}