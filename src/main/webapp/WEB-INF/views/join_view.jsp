<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/join.css">
</head>
<body>
<a href=""> <!-- 클릭 시 링크 설정 -->

			<c:forEach var="item" items="${list}">
	<div class="card">



		<!-- 카드 헤더 -->

		<div class="card-header" >

			<div class = "card-header-is_closed" > 

                <div class = "card-header-text" > 모집현황 기능전 </div > 

                <div class = "card-header-number" > 모집인원 기능전 </div > 

            </div >

		</div>



		<!--  카드 바디 -->

		<div class="card-body">



			<!--  카드 바디 헤더 -->
			<div class="card-body-header">

				<h1>${item.title}</h1>

				<p class="card-body-hashtag">헤시태그 기능전</p>

				 <p class = "card-body-nickname"> 

                                 ${item.id}

           		         </p>

			</div>

			<p class="card-body-description">

				${item.content}

			</p>
		
			<!--  카드 바디 본문 -->


			<!--  카드 바디 푸터 -->

			<div class="card-body-footer">

				<hr style="margin-bottom: 8px; opacity: 0.5; border-color: #EF5A31">

				<i class="icon icon-view_count"></i>조회수 기능전

				<i class="icon icon-comments_count"></i>댓글 기능전

				<i class="reg_date"> ${item.regDate} </i>

			</div>
	


		</div>



	</div>
			</c:forEach>

	</a>

</body>
</html>