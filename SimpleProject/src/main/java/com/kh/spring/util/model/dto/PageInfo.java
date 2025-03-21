package com.kh.spring.util.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageInfo {
	
	private int count;
	private int currentPage;
	private int boardLimit;
	private int pageLimit;
	
	private int maxPage;
	private int startPage;
	private int endPage;
	
}
