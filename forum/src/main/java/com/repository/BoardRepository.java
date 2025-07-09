package com.repository;

import com.entity.BoardVO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardVO, String>  {
	// 1. 날짜 + 시간 내림차순 정렬
	@Query(value = "SELECT * FROM BOARD_TBL ORDER BY REG_DATE DESC, REG_TIME DESC", nativeQuery = true)
	List<BoardVO> listCommon() throws Exception;

	// 2. 날짜 + 시간 내림차순 정렬 + 좋아요 10 이상 + 조회수 100 이상
	@Query(value = "SELECT * FROM BOARD_TBL WHERE LIKE_CNT >= :likeCnt AND CLICK_CNT >= :clickCnt ORDER BY REG_DATE DESC, REG_TIME DESC", nativeQuery = true)
	List<BoardVO> listBest(@Param("likeCnt") int likeCnt, @Param("clickCnt") int clickCnt) throws Exception;

	// 3. 날짜 + 시간 내림차순 정렬 + 작성자 코드 일치
	@Query(value = "SELECT * FROM BOARD_TBL WHERE REG_CODE = :regCode ORDER BY REG_DATE DESC, REG_TIME DESC", nativeQuery = true)
	List<BoardVO> listUser(@Param("regCode") String regCode) throws Exception;
}