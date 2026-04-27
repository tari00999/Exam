<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績変更</h2>

			<form action="ScoreUpdateExecute.action" method="post">

				<div class="mx-auto py-2">
					<label for="student_no">学生番号</label><br>
					<input class="border border-0 ps-3" type="text"
						id="student_no" name="student_no"
						value="${score.studentNo}" readonly />
				</div>

				<div class="mx-auto py-2">
					<label for="subject">科目</label><br>
					<input class="border border-0 ps-3" type="text"
						id="subject" name="subject"
						value="${score.subject}" readonly />
				</div>

				<div class="mx-auto py-2">
					<label for="score">点数</label><br>
					<input class="form-control" type="text"
						id="score" name="score"
						value="${score.score}" required />
				</div>

				<div class="mx-auto py-2">
					<input class="btn btn-primary" type="submit" value="変更"/>
				</div>

			</form>

			<a href="StudentList.action">戻る</a>

		</section>
	</c:param>
</c:import>