package com.entity;

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
@Entity(name = "Board")
@Table(name = "BoardTbl")
public class BoardVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idx;
	@Column(name = "BODY")
	private String body;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "REG_NICKNAME")
	private String regNickname;
	@Column(name = "REG_CODE")
	private String regCode;
	@Column(name = "REG_IP_ADDRESS")
	private String regIpAddress;
	@Column(name = "REG_TIME")
	private String regTime;
	@Column(name = "CLICK_CNT")
	private int clickCnt;
	@Column(name = "LIKE_CNT")
	private int likeCnt;
	@Column(name = "DISLIKE_CNT")
	private int dislikeCnt;
	@Column(name = "COMMENT_CNT")
	private int commentCnt;
	@Column(name = "REG_DATE")
	private String regDate;
}