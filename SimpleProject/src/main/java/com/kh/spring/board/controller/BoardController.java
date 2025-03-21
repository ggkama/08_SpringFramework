package com.kh.spring.board.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.exception.InvalidParameterException;

import lombok.RequiredArgsConstructor;
// 작업순서 - mapper - controller - service 
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
//@RequestMapping("boards")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	// ?page=1
	@GetMapping("boards")
	public String toBoardList(@RequestParam(name="page", defaultValue="1") int page, Model model) {
		
		// 한페이지에 5개 보여줌
		// 버튼 5개 보여줌
		// 총 게시글 개수 == SELECT COUTN(*) FROM TB_SPRING_BOARD WHERE STATUS = 'Y'
		if(page < 1) {
			throw new InvalidParameterException("어딜 감힝~~!");
		}
		
		Map<String, Object> map = boardService.selectBoardList(page);
		
		// 모델에 데이터 담는 방법
		model.addAttribute("map",map);
		
		
		
		return "board/board_list";
	}
	
	@GetMapping("form.bo")
	public String goToForm() {
		return "board/insert_board";
	}
	
	@PostMapping("boards")
	public ModelAndView newBoard(ModelAndView mv,
								 BoardDTO board,
								 MultipartFile upfile,
								 HttpSession session) {
		log.info("게시글 정보 {}, 파일정보 : {}",board,upfile);
		
		// 첨부파일의 존재유무
		// => 차이점 => MultipartFile타입의 filename필드값으로 확인을 하겠다
		
		// INSERT INTO TB_SPRING_BOARD(BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER,
		// 							   CHANGE_NAME)
		//				VALUES (#{boardTitle}, #{boardContent}, #{boardWriter}, #{changeName});
		
		// 1. 권한있는 요청인지 확인 
		// 2. 값들이 유효성이 있는 값인가
		// 3. 전달된 파일이 존재할 경우 => 파일명 수정 서버에 올리고 
		//	  BoardDTO의 changeName필드에 값을 대입해서 insert실행
		boardService.insertBoard(board, upfile,session);
		mv.setViewName("redirect:boards");
		session.setAttribute("message", "게시글 등록 성공 ");
		return mv;
		
	}
	
	@GetMapping("boards/{id}")
	public ModelAndView goBoard(@PathVariable(name="id") int boardNo, ModelAndView mv) {
		log.info("게시글번호 : {}", boardNo);
		if(boardNo < 1) {
			throw new InvalidParameterException("비정상적인 접근");
		}
		
		BoardDTO board = boardService.selectBoard(boardNo);
		mv.addObject("board", board).setViewName("board/board_detail");
		return mv;
	}
	
	
}
