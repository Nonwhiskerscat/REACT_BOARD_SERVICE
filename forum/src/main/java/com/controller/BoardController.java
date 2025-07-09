package com.controller;

import com.dto.*;
import com.entity.BoardVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.LoginVO;
import com.service.BoardService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.ibatis.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired BoardService boardService;
	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<BoardVO> list(@RequestParam Map<String, String> params) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		LoginVO loginVO = session == null ? null : (LoginVO) session.getAttribute("loginVO");
		String mode = params.get("mode");
		List<BoardVO> result = new ArrayList<>();
		
		switch(mode) {
			case "best":
				// 특별한 설정이 없으면 좋아요 수 10개, 조회수 100회
				int likeCut  = Integer.parseInt(params.getOrDefault("likeCut", "10"));
				int clickCut = Integer.parseInt(params.getOrDefault("clickCut", "100"));
				result = boardService.getBestList(likeCut, clickCut);
				break;

			case "my": 
				String regCode = loginVO.getCode();
				result = boardService.getUserList(regCode);
				break;

			default:
				result = boardService.getCommonList();
				break;
		}

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public Object write(@RequestParam Map<String, String> params) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		LoginVO loginVO = session == null ? null : (LoginVO) session.getAttribute("loginVO");
		params.put("regCode", loginVO.getCode());
		params.put("regNickname", loginVO.getNickname());

		String regIpAddress = request.getRemoteAddr();
		params.put("regIpAddress", regIpAddress);

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

		String regDate = now.format(dateFormatter);
		String regTime = now.format(timeFormatter);

		params.put("regDate", regDate);
		params.put("regTime", regTime);

		BoardVO boardVO = mapper.convertValue(params, BoardVO.class);
		boardService.save(boardVO);

		return Map.of("result","success","boardCode", boardVO.getRegCode());
	}

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public Object view(@RequestParam Map<String, String> params) throws Exception {
		String idx = params.get("idx");
		String click = params.get("click");
        try {
			BoardVO boardVO = boardService.findById(idx);

			/* 조회수 + 1*/
			if(Boolean.parseBoolean(click)) {
				boardVO.setClickCnt(boardVO.getClickCnt() + 1);
				boardService.save(boardVO);
			}

			return boardVO;
		}
		catch(NoSuchElementException e) {
			return Map.of("result", "failed", "message", "게시글을 찾을 수 없습니다.");
		}
    }

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(@RequestParam Map<String, String> params) throws Exception {
		String idx = params.get("idx");
		try {
			boardService.deleteById(idx);
			return Map.of("result", "success");
		} catch (NoSuchElementException e) {
			return Map.of("result", "failed", "message", "이미 삭제된 글입니다.", "type", "notfound");
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(@RequestParam Map<String, String> params) throws Exception {
		String idx = params.get("idx");
		String title = params.get("title");
		String body = params.get("body");

		try {
			BoardVO boardVO = boardService.findById(idx);
			boardVO.setTitle(title);
			boardVO.setBody(body);
			boardService.save(boardVO); // 저장 (update)
			return Map.of("result", "success");
		} catch (NoSuchElementException e) {
			return Map.of("result", "failed", "message", "수정할 게시글이 존재하지 않습니다.");
		}
	}
	
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public Object feedback(@RequestParam Map<String, String> params) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		HttpSession session = request.getSession();
		LoginVO loginVO = session == null ? null : (LoginVO) session.getAttribute("loginVO");

		String code = loginVO.getCode();
		String idx = params.get("idx");
		String target = params.get("target");
		String targetKor = "좋아요";
		if ("dislike".equals(target)) targetKor = "싫어요";

		String cookieName = code + ("like".equals(target) ? "_boardLike" : "_boardDislike");

		// 쿠키 조회 및 파싱
		Set<String> cookieIdset = new HashSet<>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					String value = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
					ObjectMapper mapper = new ObjectMapper();
					try {
						String[] arr = mapper.readValue(value, String[].class); // JSON 배열 그대로 파싱
						cookieIdset.addAll(Arrays.asList(arr));
					} catch (Exception e) {
						// 파싱 실패 시 무시
					}
					break;
				}
			}
		}

		if (cookieIdset.contains(idx)) {
			return Map.of("result", "failed", "message", "이미 " + targetKor + " 하셨습니다.", "type", "already");
		}

		try {
			BoardVO board = boardService.findById(idx);
			if ("like".equals(target)) board.setLikeCnt(board.getLikeCnt() + 1);
			else if ("dislike".equals(target)) board.setDislikeCnt(board.getDislikeCnt() + 1);
			boardService.save(board);

			cookieIdset.add(idx);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(cookieIdset); // 예: ["2","3","4"]
			String cookieValue = URLEncoder.encode(json, StandardCharsets.UTF_8);

			LocalDateTime now = LocalDateTime.now();
			LocalDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay();
			long secondsUntilMidnight = ChronoUnit.SECONDS.between(now, midnight);

			Cookie updatedCookie = new Cookie(cookieName, cookieValue);
			updatedCookie.setPath("/");
			updatedCookie.setMaxAge((int) secondsUntilMidnight);
			response.addCookie(updatedCookie);

			return Map.of("result", "success", "count", "like".equals(target) ? board.getLikeCnt() : board.getDislikeCnt());
		} catch (NoSuchElementException e) {
			return Map.of("result", "failed", "message", "게시글을 찾을 수 없습니다.", "type", "notfound");
		}
	}


}