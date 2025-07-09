package com.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Comment")
@Table(name = "CommentTbl")
public class CommentVO {
	@Id
	private int commentIdx;
	private int boardIdx;
	private String code;
	private String commentUserProfile;
	private String commentUserNickname;
	private String commentWriteDate;
	private String commentContent;
}