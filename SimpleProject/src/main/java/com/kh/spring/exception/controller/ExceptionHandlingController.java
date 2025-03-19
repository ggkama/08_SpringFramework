package com.kh.spring.exception.controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.exception.MemberNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {
	
	private ModelAndView createErrorResponse(String errorMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", e.getMessage()).setViewName("include/error_page");
		log.info("발생예외 : {} ", errorMsg, e);
		return mv;
	}
	
	@ExceptionHandler(PasswordNotMatchException.class)
	protected ModelAndView PasswordNotMatchException(PasswordNotMatchException e) {
		return createErrorResponse(e.getMessage(), e);	
	}

	@ExceptionHandler(MemberNotFoundException.class)
	protected ModelAndView MemberNotFoundException(MemberNotFoundException e) {
		return createErrorResponse(e.getMessage(), e);	
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	protected ModelAndView invalideParameterError(InvalidParameterException e) {
		return createErrorResponse(e.getMessage(), e);	
	}
	
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView TooLargeValueException(TooLargeValueException e) {
		return createErrorResponse(e.getMessage(), e);	
	}
	

	
	/*
	@ExceptionHandler(InvalidParameterException.class)
	protected ModelAndView invalideParameterError(InvalidParameterException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", e.getMessage()).setViewName("include/error_page");
		return mv;
	}
	
	
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView tooLargeValueError(TooLargeValueException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", e.getMessage()).setViewName("include/error_page");
		return mv;
	}
	*/
	
	
}
