package com.kh.spring.board.model.dto;

import java.util.List;

import com.kh.spring.reply.model.dto.ReplyDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// ValueObject == 불변성

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class BoardDTO {
	// 계층간의 데이터 전송
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private int count;
	private String createDate;
	private String changeName;
	private String status;
	private List<ReplyDTO> replyList;
}
