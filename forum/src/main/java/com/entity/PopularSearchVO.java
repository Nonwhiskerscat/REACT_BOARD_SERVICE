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
@Entity(name = "PopularSearch")
@Table(name = "PopularSearchTbl")
public class PopularSearchVO {
	@Id
	private String popularTerm;
	private int popularSearchCnt;
}