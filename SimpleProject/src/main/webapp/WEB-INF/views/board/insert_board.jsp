<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .content {
            background-color:rgb(247, 245, 245); 
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }

        #enrollForm>table {width:100%;}
        #enrollForm>table * {margin:5px;}
        #img-area{
            width : 100%;
            margin : auto;
            text-align: center;
        }
        #img-area > img{
            width : 80%;
        }
    </style>
</head>
<body>
        
    <jsp:include page="../include/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 작성하기</h2>
            <br>

            <form id="enrollForm" method="post" action="boards" enctype="multipart/form-data">
                <table align="center">
                    <tr>
                        <th><label for="title">제목</label></th>
                        <td><input type="text" id="title" class="form-control" name="boardTitle" required></td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td><input type="text" id="writer" class="form-control" value="${sessionScope.loginMember.memberId }" name="boardWriter" readonly></td>
                    </tr>
                    <tr>
                        <th><label for="upfile">첨부파일</label></th>
                        <td><input onchange="changeImage(this);" type="file" id="upfile" class="form-control-file border" name="upfile"></td>
                    </tr>
                    <tr>
                        <th><label for="content">내용</label></th>
                        <td><textarea id="content" class="form-control" rows="10" style="resize:none;" name="boardContent" required></textarea></td>
                    </tr>
                    <tr>
                        <th colspan="2">
                            <div id="img-area">
                                <img src="https://t1.kakaocdn.net/friends/www/talk/kakaofriends_talk_2018.png" alt="">
                            </div>
                        </th>
                    </tr>
                </table>
                <br>
                
                <script>
                	function changeImage(file){
						
                		//console.log(file);
                		
                		// console.log(file.files);
                		// files : 선택된 파일의 정보가 들어있는 객체
                		
                		// file.files.length == 1 파일선택됨
                		// file.files.length == 0 선택취소함
                		
                		// 파일이 첨부되었을 경우에는 file.files
                		// 0번속성을 보면 파일 정보를 확인할 수 있음
                		// console.log(file.files[0]);
                		const imgEl = document.querySelector('#img-area>img');
                		if(file.files.length){ // 파일이 첨부
                			
                			const reader = new FileReader();
                			reader.readAsDataURL(file.files[0]);
                			
                			reader.onload = function(e){
								//console.log(e.target.result);
								const url = e.target.result;
								imgEl.src = url;
                			}
                		} else {
                			const img = 'https://i.namu.wiki/i/pNmqGhmQWMeYWsRW7hTEnMrArDksFnC_0b31GBjW5cuy0cWxrkHRj-CStWqt3PXN48g6fO4oC_kXqMlumaJEJw.gif';
                			imgEl.src = img;
                		}
                	}
                </script>
                
                <div align="center">
                    <button type="submit" class="btn btn-primary">등록하기</button>
                    <button type="reset" class="btn btn-danger">취소하기</button>
                </div>
            </form>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="../include/header.jsp" />
    
</body>
</html>