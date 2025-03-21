package com.kh.spring.util.Template;

import com.kh.spring.util.model.dto.PageInfo;

public class pagination {
	
	public static PageInfo getPageInfo(int count,
									   int currentPage,
									   int boardLimit,
									   int pageLimit) {
		
		int maxPage = (int)Math.ceil((double)count / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) { endPage = maxPage; }
		
		/*
		new PageInfo(count, currentPage, boardLimit, pageLimit, maxPage, startPage, endPage);
		
		PageInfo page = new PageInfo();
		page.setBoardLimit(boardLimit);
		page.setCount(count);
		page.setCurrentPage(currentPage);
		*/
		
		return PageInfo.builder().
						boardLimit(boardLimit).
						count(count).
						currentPage(currentPage).
						startPage(startPage).
						endPage(endPage).
						maxPage(maxPage).
						pageLimit(pageLimit).
						build();
		
	}
	

}
