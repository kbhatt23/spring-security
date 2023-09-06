package com.learning.springsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "xss_article")
@Data
@NoArgsConstructor
public class XSSArticleEntity {

	@Id
	@Column(name = "article_id")
	private int articleId;
	
	private String article;
	
	
}
