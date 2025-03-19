package com.kh.spring.member.model.service;

import java.security.InvalidParameterException;

import javax.management.RuntimeErrorException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	//@Autowired
	private final MemberDAO memberDao;
	//@Autowired
	private final SqlSessionTemplate sqlSession;
	private final BCryptPasswordEncoder passwordEncoder;
	private final MemberValidator validator;
	
	/*
	@Autowired
	public MemberServiceImpl(MemberDAO memberDao, SqlSessionTemplate sqlSession) {
		this.memberDao = memberDao;
		this.sqlSession = sqlSession;
	}
	*/

		
		
	@Override
	public MemberDTO login(MemberDTO member) {
		// 로그인이라는 요청을 처리해주어야하는데
		// 아이디가 10자 넘으면 x
		// 아이디/비밀번호가 빈문자열 또는 null x
		validator.validatedLoginMember(member);
		
		

		// 1. table에 아이디가 존재해야함
		// 2. 비밀번호가 일치해야함
		// 3. 둘다 통과면 정상적으로 조회할 수 있도록 해야함
		/* DAO로 이동해야함
		 * SqlSession sqlSession = getSqlSession();
		 * MemberDTO loginMember = new MemeberDAO().login(sqlSession, member);
		 * sqlSession.close();
		 * return loginMember;
		 */
		
		MemberDTO loginMember = memberDao.login(sqlSession, member);
		// 아이디만 일치하더라도 행의 정보를 필드에 담아옴
		
		// 1. loginMember가 null값과 동일하다면 아이디가 존재하지 않는다.
		if(loginMember == null) {
			throw new MemberNotFoundException("존재하지 않는 아이디입니다.");
		}
		
		// 2. 아이디만 가지고 조회를 하기 때문에
		// 비밀번호를 검증 후
		// 비밀번호가 유효하다면 회원의 정보를 session에 담기
		// 비밀번호가 유효하지않다면 비밀번호 이상한데?
		if(passwordEncoder.matches(member.getMemberPw(), loginMember.getMemberPw())) {
			return loginMember;
		}else {
			throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다");
		}
		
	}
		
	

	@Override
	public void signUp(MemberDTO member) {
		
		if(member == null 
				|| member.getMemberId() == null 
				|| member.getMemberId().trim().isEmpty()
				|| member.getMemberPw() == null
				|| member.getMemberPw().trim().isEmpty()) {
			return;
		}
		
		int result = memberDao.checkId(sqlSession, member.getMemberId());
		
		if(result > 0) { return; }
		
		//log.info("사용자가 입력한 비밀번호 평문 : {}", member.getMemberPw());
		// 암호화하는법 : .encode() 호출
		//log.info("평문을 암호문으로 바꾼것 {}", passwordEncoder.encode(member.getMemberPw()));
		
		
		// INSERT
		
		String encPwd = passwordEncoder.encode(member.getMemberPw());
		member.setMemberPw(encPwd);
		int consequence = memberDao.signUp(sqlSession, member);
		
		if(consequence > 0) {
			return;
		}else {
			return;
		}
		
		
		
		
	}

	@Override
	public MemberDTO update(MemberDTO member) {
		
		return null;
	}

	@Override
	public int delete(MemberDTO member) {
		
		return 0;
	}
	
	
	
}
