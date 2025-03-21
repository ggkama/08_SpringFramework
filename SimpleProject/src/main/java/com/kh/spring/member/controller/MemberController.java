package com.kh.spring.member.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.service.MemberServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor // 의존성주입 생성자를 생성해주는 애노테이션
public class MemberController {

	// @Autowired
	private final MemberService memberService;

	/*
	 * 2번
	 * 
	 * @Autowired public void setMemberService(MemberService memberService) {
	 * this.memberService = memberService; }
	 */

	/*
	 * // 권장 : 3번 -> 생성자에 Autowired를 붙이는 방법
	 * 
	 * @Autowired public MemberController(MemberService memberService){
	 * this.memberService = memberService; }
	 */

	/*
	 * @RequestMapping(value="login") public String login(HttpServletRequest
	 * request) { //System.out.println("로그인 요청오면 슛~"); String id =
	 * request.getParameter("id"); String pw = request.getParameter("pw");
	 * //System.out.printf("id : %s, pw : %s", id, pw); log.info("id : {}, pw : {}",
	 * id, pw); return "main_page"; }
	 */

	/*
	 * @RequestMapping("login") public String login(@RequestParam(value="id") String
	 * id,
	 * 
	 * @RequestParam(value="pw")String pw) {
	 * 
	 * log.info("이거도 넘어옴? id {} / pw : {}", id, pw);
	 * 
	 * return "main_page"; }
	 */

	/*
	 * @PostMapping("login") public String login(String id, String pw) {
	 * 
	 * log.info("이래도 넘어옴? id:{}/pw:{}",id,pw);
	 * 
	 * MemberDTO member = new MemberDTO(); member.setMemberId(id);
	 * member.setMemberPw(pw);
	 * 
	 * 
	 * return "main_page"; }
	 */

	/**
	 * 커맨드 객체 방식
	 * 
	 * 1. 매개변수 자료형에 반드시 기본생성자가 존재할 것 2. 전달되는 키값과 객체의 필드명이 동일할 것 3. setter메서드가 반드시
	 * 존재할 것
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 통해서 생성한 후 내부적으로 setter 메서드를 찾아서 요청시 해당 필드값에 대입해줌 (Setter
	 * Injection)
	 */

	/*
	 * @PostMapping("login") public String login(MemberDTO member, HttpSession
	 * session, Model model) {
	 * 
	 * // log.info("이건 안돼 ㅇㅇ {} ", member);
	 * 
	 * 컨트롤러가 해야하는 것 데이터 가공 => 스프링 프레임워크가 대신해줌 => 패스 요청처리 => 서비스가 해줌 => 응답화면 지정
	 * 
	 * 
	 * MemberDTO loginMember = memberService.login(member);
	 * 
	 * 
	 * if(loginMember != null) { log.info("로그인 성공"); }else { log.info("로그인 실패"); }
	 * 
	 * 
	 * if(loginMember != null) { // 성공했을때 // sessionScope에 로그인 정보를 담아줌
	 * session.setAttribute("loginMember", loginMember); // 그 다음에 main_page로 이동 //
	 * /WEB-INF/views/ // .jsp // => 포워딩 // SendRedirect
	 * 
	 * // localhost/spring / return "redirect:/";
	 * 
	 * } else { // 실패했을때
	 * 
	 * // error_page로 이동 // requestScope에 에러문구를 담아서 포워딩 // Spring에서는 Model객체를 이용해서
	 * RequestScope에 값을 담음 model.addAttribute("message","로그인 실패");
	 * 
	 * // fowarding // WEB-INF/views/ // include/error_page // .jsp return
	 * "include/error_page"; }
	 * 
	 * //return "main_page";
	 * 
	 * 
	 * }
	 */
	
	@PostMapping("login")
	public ModelAndView login(MemberDTO member,
							  HttpSession session,
							  ModelAndView mv) {
		MemberDTO loginMember = memberService.login(member);
		
		if(loginMember !=null) {
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("message", "로그인실패함")
			  .setViewName("include/error_page");
		}
		
		return mv;
	}
	
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session,
							   ModelAndView mv) {
		
		session.removeAttribute("loginMember");
		mv.setViewName("redirect:/");
		
		return mv;
	}
	
	@GetMapping("signup-form")
	public String signupForm() {
		// /WEB-INF/views/member/signup-form.jsp
		return "member/signup-form";
	}
	
	
	
	/**
	 * @param member id 비번 등등등...
	 * 
	 * @return 성공시 main 실패하면 err담아서 error_page
	 */
	@PostMapping("signup")
	public String join(MemberDTO member) {
		/*
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		*/
		
		
		// log.info("멤버 필드 찍어보기{}",member);
		memberService.signUp(member);
		return "main_page";
		
	}

	@GetMapping("my-page")
	public String myPage() {
		return "member/my_page";
	}
	
	@PostMapping("update-member")
	public String update(MemberDTO member, HttpSession session) {
		// 1. Controller에서는 RequestMapping 애노테이션 및 요청 시 전달값이 잘 전달되는지 확인
		/*
		 * 1_1) 404 발생 : mapping 값 잘못 적음
		 * 
		 * 1_2) 405 발생 : 앞단에서 POST / GET으로 보내놓고 메서드와 맞지않은 애노테이션을 사용했을 떄
		 * 
		 * 1_3) 필드에 값이 들어오지 않은 경우 (DTO를 잘 구현했다는 가정) (필드명, 기본생성자 ,getter setter등)
		 * 		-> Name속성값을 잘못 입력함(오타!!!!) 
		 */
		
		// 2. 이번에 실행할 SQL문을 생각
		 
		 // UPDATE문을 사용할때 필요한 값들을 다 담아왔는지 체크
		 // ID, PW, NAME, EMAIL, DATE 
		 // 2_1) 매개변수 MemberDTO타입의 memberId 필드값
		 // 2_2) SessionScope의 loginMember 키값의 memberId 필드값
		 // 넘겨줘야겠구나 +
		  
		 // 값들이 유요한 값인지 체크하기
		 // MemberID가 존재하는 아이디인지 체크하기 
		  
		 // UPDATE KH_MEMBER SET MEMBER_NAME = 사용자가 입력한 이름,
		 //						  EMAIL = 사용자가 입력한 이메일
		 //					WHERE MEMBER_ID = 사용자가 입력한 아이디
		 // UPDATE 수행의 결과 => PK를 조건으로 수행함 => 0 / 1
		  
		 // 수행에 성공했을 경우 =>
		 // my_page.jsp로 이동 + 갱신된 회원의 정보 출력
		 // Id를 가지고 다시 조회 => login메서드 재활용
		  
		  
		 // 수행에 실패했을 경우 =>
		 // message를 담아서 error_page로 포워딩
		 // 예외발생 => 예외처리기로 위임
		 
		// log.info("사용자가 입력한 값 : {}",member);
		
		memberService.update(member, session);
		
		return "redirect:my-page";
		
	}
	
	// 탈퇴구현 숙제로하기
	// 비밀번호 입력받음
	// 비밀번호가 맞는지 검증 => 예외 발생시키기
	// DELETE성공했는지 검증 
	
}
