<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
<title>Manger</title>
<!--↓ 이부분은 managerpage에 적용될 css가 담겨있는 iclude 태그입니다. 포함하셔야 됩니다. -->
<%@include file="../../commons/mh.jsp"%>
<%@ include file="option.jsp"%>
</head>
<body>
<!--↓아래에 있는 section id="container"는 꼭 만드서야 합니다 !  -->
<section id="container" class="">
<!--↓밑에 두개 include 파일은 복사 하셔서 만드시는 뷰단 페이지에다가 붙여넣기 하세요 꼭 이위치에 복사 하셔야 됩니다! -->
	<%@include file="../../commons/mhd.jsp"%>
	<%@include file="../../commons/mfj.jsp"%>
<!-- ↓id="main-content" section은 작성하시는 코드가 들어가는 부분입니다. 만드셔야지 되요!  -->
	<section id="main-content">
<!--      ↓여기있는 wrapper는 감싸주는거 같아요 저도 잘 모르겠어요 일단 만드셔야 됩니다. -->
		<section class="wrapper">
		  <!-- ↓ 하나의 코드를 작성 하실떄 div class="row"는  하나의 행을 의미합니다.  -->
		 <div class="row">
		 <div class="col-lg-12">
		 <h3 class="page-header">Member List</h3>
		 <table class="table table-striped table-advance table-hover">
                <tbody>
                  <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Mobile</th>
                    <th>Date</th>
                    <th>Action</th>
                  </tr>
            	  <c:forEach var="row" items="${list}">
					<!-- 컨트롤러에서 넘어온 변수 -->
				  <tr>
					<td>${row.uid}</td>
					<td>${row.uname}</td>
					<td>${row.uphone}</td>
					<td><fmt:formatDate value="${row.udate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<%-- <td><a href="${path}/member/view.do?userid=${row.userid}">${row.name}</a></td> --%>
					 <td>
                      <div class="btn-group">
                        <a class="btn btn-primary" href="/manager/member/view.do?uid=${row.uid}">조회</a>
                        <a class="btn btn-danger" href="/manager/member/delete.do">삭제</a>
                      </div>
                    </td>
				  </tr>
				 </c:forEach>        
                </tbody>
         </table>
		 </div>
        </div>
        </section>
        <div class="row">
          <div class="col-lg-12">
            <section class="panel">
              <header class="panel-heading">
                Form validations
              </header>
              <div class="panel-body">
                <div class="form">
                  <form class="form-validate form-horizontal" id="feedback_form" method="get" action="">
                    <div class="form-group ">
                      <label for="cname" class="control-label col-lg-2">Full Name <span class="required">*</span></label>
                      <div class="col-lg-10">
                        <input class="form-control" id="cname" name="fullname" minlength="5" type="text" required />
                      </div>
                    </div>
                    <div class="form-group ">
                      <label for="cemail" class="control-label col-lg-2">E-Mail <span class="required">*</span></label>
                      <div class="col-lg-10">
                        <input class="form-control " id="cemail" type="email" name="email" required />
                      </div>
                    </div>
                    <div class="form-group ">
                      <label for="curl" class="control-label col-lg-2">Website</label>
                      <div class="col-lg-10">
                        <input class="form-control " id="curl" type="url" name="url" />
                      </div>
                    </div>
                    <div class="form-group ">
                      <label for="cname" class="control-label col-lg-2">Subject <span class="required">*</span></label>
                      <div class="col-lg-10">
                        <input class="form-control" id="subject" name="subject" minlength="5" type="text" required />
                      </div>
                    </div>
                    <div class="form-group ">
                      <label for="ccomment" class="control-label col-lg-2">Feedback</label>
                      <div class="col-lg-10">
                        <textarea class="form-control " id="ccomment" name="comment" required></textarea>
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="col-lg-offset-2 col-lg-10">
                        <button class="btn btn-primary" type="submit">Save</button>
                        <button class="btn btn-default" type="button">Cancel</button>
                      </div>
                    </div>
                  </form>
                </div>
		</section>
	</section>
</section>
   <!-- ↓ 이부분은 자바스크립트가 작성된 include 태그입니다. 포함 하셔야됩니다 ! -->
	<%@include file="../../commons/mjs.jsp"%>
</body>
</html>