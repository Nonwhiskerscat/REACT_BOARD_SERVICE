package com.repository;

import com.entity.UserVO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserVO, String> {
	public UserVO findByCode(String code) throws Exception;
	public boolean existsByCode(String code) throws Exception;
    public boolean existsByCodeAndPassword(String code, String password) throws Exception;
}