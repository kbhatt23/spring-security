package com.learning.springsecurity.dtos;

import java.util.List;

import com.learning.springsecurity.entity.XSSArticleEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class XssArticleSearchResponse {

	private String queryCount;
	
	private List<XSSArticleEntity> result;
}
