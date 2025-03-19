package com.kh.spring.member.model.service;



import org.springframework.stereotype.Component;

import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dto.MemberDTO;

@Component
public class MemberValidator {

	public void validatedLength(MemberDTO member) {
	if(member.getMemberId().length() > 10 ) {
		throw new TooLargeValueException("아이디 값이 너무 깁니다. 10자 이내로 작성해주세요.");
	}
	}
	
	public void validatedValue(MemberDTO member) {
		if(member == null 
				|| member.getMemberId() == null 
				|| member.getMemberId().trim().isEmpty()
				|| member.getMemberPw() == null
				|| member.getMemberPw().trim().isEmpty()) {
			 		throw new InvalidParameterException("입력이 안되어있거나, 공백이 있어요");
		}	
	}
		
	public void validatedLoginMember(MemberDTO member) {
		validatedLength(member);
		validatedValue(member);
		
	}
}
