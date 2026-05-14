<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="content">

	<section>
		<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
			成績登録
		</h2>

		<!-- 検索 -->
		<form action="TestRegist.action" method="get" class="px-4">

			<div class="mb-3">
				<label>入学年度</label>
				<select name="ent_year" class="form-select">
					<option value="">--選択--</option>
					<c:forEach var="y" items="${ent_year_set}">
						<option value="${y}" <c:if test="${y == ent_year}">selected</c:if>>
							${y}
						</option>
					</c:forEach>
				</select>
			</div>

			<div class="mb-3">
				<label>クラス</label>
				<select name="class_num" class="form-select">
					<option value="">--選択--</option>
					<c:forEach var="c" items="${class_num_set}">
						<option value="${c}" <c:if test="${c == class_num}">selected</c:if>>
							${c}
						</option>
					</c:forEach>
				</select>
			</div>

			<div class="mb-3">
				<label>科目</label>
				<select name="subject_cd" class="form-select">
					<option value="">--選択--</option>
					<c:forEach var="s" items="${subject_set}">
						<option value="${s.cd}" <c:if test="${s.cd == subject_cd}">selected</c:if>>
							${s.name}
						</option>
					</c:forEach>
				</select>
			</div>

			<div class="mb-3">
				<label>回数</label>
				<input type="number" name="no" value="${no}" class="form-control" min="1">
			</div>

			<button class="btn btn-primary">検索</button>
		</form>

		<hr>
		
		<!-- メッセージ表示 -->
		<c:if test="${message != null}">
    		<div class="alert alert-warning mx-4">
        		${message}
    		</div>
		</c:if>

		<!-- 結果 -->
		<c:if test="${students != null}">

			<form action="TestRegistExecute.action" method="post" class="px-4">

				<input type="hidden" name="subject_cd" value="${subject_cd}">
				<input type="hidden" name="no" value="${no}">
				<input type="hidden" name="class_num" value="${class_num}">

				<table class="table table-bordered text-center">
					<tr>
						<th>学生ID</th>
						<th>名前</th>
						<th>点数</th>
					</tr>

					<c:forEach var="st" items="${students}">
						<tr>
							<td>${st.no}</td>
							<td>${st.name}</td>
							<td>
								<input type="hidden" name="student_no" value="${st.no}">
								<input type="number" name="score" class="form-control" min="0" max="100">
							</td>
						</tr>
					</c:forEach>

				</table>

				<button class="btn btn-secondary">登録して終了</button>

			</form>

		</c:if>

	</section>

	</c:param>
</c:import>