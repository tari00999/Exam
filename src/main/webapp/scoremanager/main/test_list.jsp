<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="content">

	<section>
		<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
			成績参照
		</h2>

		<!-- ================= 科目・クラス検索 ================= -->
		<form action="TestListSubjectExecute.action" method="get" class="px-4">

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
				<div class="text-danger">${errors.ent_year}</div>
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
				<div class="text-danger">${errors.class_num}</div>
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
				<div class="text-danger">${errors.subject_cd}</div>
			</div>

			<button class="btn btn-primary">クラス検索</button>

		</form>

		<hr>

		<!-- ================= 学生検索 ================= -->
		<form action="TestListStudentExecute.action" method="get" class="px-4">

			<div class="mb-3">
				<label>学生番号</label>
				<input type="text" name="student_no"
					value="${student_no}" class="form-control">
				<div class="text-danger">${errors.student_no}</div>
			</div>

			<button class="btn btn-primary">学生検索</button>

		</form>

		<hr>

		<!-- ================= 検索結果 ================= -->
		<c:if test="${tests != null && tests.size() > 0}">

			<table class="table table-bordered text-center">
				<tr>
					<th>学生ID</th>
					<th>名前</th>
					<th>科目</th>
					<th>回数</th>
					<th>点数</th>
				</tr>

				<c:forEach var="t" items="${tests}">
					<tr>
						<td>${t.student.no}</td>
						<td>${t.student.name}</td>
						<td>${t.subject.cd}</td>
						<td>${t.no}</td>
						<td>${t.point}</td>
					</tr>
				</c:forEach>

			</table>

		</c:if>

		<!-- データなし -->
		<c:if test="${tests != null && tests.size() == 0}">
			<div class="text-danger text-center">
				該当するデータが存在しません
			</div>
		</c:if>

	</section>

	</c:param>
</c:import>