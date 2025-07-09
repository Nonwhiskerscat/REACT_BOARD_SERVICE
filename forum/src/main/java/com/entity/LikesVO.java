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
@Entity(name = "Likes")
@Table(name = "LikesTbl")
public class LikesVO {
	@Id
	private int likeIdx;
	private int boardIdx;
	private String code;
	private String likeUserProfile;
	private String likeUserNickname;
}