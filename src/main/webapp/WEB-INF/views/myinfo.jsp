<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
<link href="/css/myInfo.css" rel="stylesheet" type="text/css" />
<%@ include file="header.jsp"%>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
	integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<c:if test="${not empty sessionScope.userId}">
	<c:set var="user" value="${userDto}" />
</c:if>



<div class="infoEdit-container">

	<div class="infoEdit-box">
		<div class="infoEdit">
			<form action="/myinfo" method="post">
				<input type="text" class="text input-f" name="userId" value="${user.userId}" readonly="readonly"> <span>아이디</span><br>
				<br> <input type="password" class="text input-f" name="password"> <i class="fa fa-eye fa-lg"></i><span>비밀번호</span><br>
				<br> <input type="email" class="text input-f" name="email" value="${user.email}"> <span>이메일</span><br>
				<br> <input type="text" class="text input-f" name="name" value="${user.name}"> <span>이름</span><br>
				<br> <input type="text" class="text input-f" name="nick" value="${user.nick}"> <span>닉네임</span><br>
				<br> <input type="tel" class="text input-f" name="tel" value="${user.tel}"> <span>전화번호</span><br> <br>


				<div class="sel-box">
					<select class="sel-text">
						<option selected>성별</option>
						<option value="F">여자</option>
						<option value="M">남자</option>
					</select> <select class="sel-text">
						<option selected>년도</option>
						<c:set var="year" value="2023"></c:set>
						<c:forEach var="i" begin="1900" end="${year}" step="1">
							<option value="${year - i + 1900}">${year - i + 1900}년</option>
						</c:forEach>
					</select> <select class="sel-text">
						<option selected>월</option>
						<c:forEach var="i" begin="1" end="12" step="1">
							<option value="${i}">${i}월</option>
						</c:forEach>
					</select> <select class="sel-text">
						<option selected>일</option>
						<c:forEach var="i" begin="1" end="31" step="1">
							<option value="${i}">${i}일</option>
						</c:forEach>
					</select>
				</div>
				<div class="submitbox">
					<button id="update" type="submit">수정하기</button>
					<button id="dropout" type="submit">탈퇴하기</button>
				</div>

			</form>
		</div>
	</div>
</div>

</div>
<%@ include file="footer.jsp"%>

<script>
    // 비밀번호 눈알
    $(document).ready(function() {
        $('.infoEdit i').on('click', function() {
            var passwordInput = $(this).prev('input[name="password"]');
            passwordInput.toggleClass('active');
            if (passwordInput.hasClass('active')) {
                $(this).attr('class', "fa fa-eye-slash fa-lg");
                passwordInput.attr('type', 'text');
            } else {
                $(this).attr('class', "fa fa-eye fa-lg");
                passwordInput.attr('type', 'password');
            }
        });

        // 수정하기 버튼 클릭 이벤트
        $('#update').click(function(event) {
            event.preventDefault(); // 폼의 기본 동작인 submit을 막음

            let userId = $('input[name="userId"]').val();
            let password = $('input[name="password"]').val();
            let email = $('input[name="email"]').val();
            let name = $('input[name="name"]').val();
            let nick = $('input[name="nick"]').val();
            let tel = $('input[name="tel"]').val();

            $.ajax({
                type: "POST", // 요청 method
                contentType: "application/json; charset=utf-8", // json 포맷 utf-8 내용으로 통신하겠다
                url: "/myinfo", // 어디 경로로 요청할 건지
                data: JSON.stringify({ // 객체를 -> JSON string 으로 변환
                    userId: userId,
                    password: password,
                    email: email,
                    name: name,
                    nick: nick,
                    tel: tel
                }), // 파라미터로 같이 담아서 보낼 것들
                success: function(data) {
                    if (data === 'true') {
                        alert('수정완료');
                    } else if (data === 'false') {
                        alert('수정실패');
                    }
                    window.location.href = "/myinfo";
                }, // 요청에 대해 성공한 경우 수행할 내용
                error: function() {
                    alert('실행 오류');
                } // 요청이 실패, 오류난 경우 수행할 내용
            });
        });
    });
</script>