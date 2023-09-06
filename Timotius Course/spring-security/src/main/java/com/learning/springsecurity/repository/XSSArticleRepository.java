package com.learning.springsecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.springsecurity.entity.XSSArticleEntity;

@Repository
public interface XSSArticleRepository extends JpaRepository<XSSArticleEntity, Integer>{

	public List<XSSArticleEntity> findByArticleContainsIgnoreCase(String article);
}
