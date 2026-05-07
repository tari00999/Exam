<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="content">

	<section>
		<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
			成績参照結果
		</h2>

		<!-- エラーメッセージ -->
		<c:if test="${errors != null}">
			<div class="alert alert-danger mx-4">
				<c:forEach var="e" items="${errors}">
					<div>${e.value}</div>
				</c:forEach>
			</div>
		</c:if>

		<!-- データなし -->
		<c:if test="${tests == null || tests.size() == 0}">
			<div class="alert alert-warning mx-4">
				該当するデータが存在しません
			</div>
		</c:if>

		<!-- 結果表示 -->
		<c:if test="${tests != null && tests.size() > 0}">

			<table class="table table-bordered text-center mx-4">
				<tr>
					<th>学生ID</th>
					<th>名前</th>
					<th>点数</th>
				</tr>

				<c:forEach var="t" items="${tests}">
					<tr>
						<td>${t.student.no}</td>
						<td>${t.student.name}</td>
						<td>${t.point}</td>
					</tr>
				</c:forEach>

			</table>

		</c:if>

		<!-- 戻る -->
		<div class="mx-4 mt-3">
			<a href="TestList.action" class="btn btn-secondary">
				戻る
			</a>
		</div>

	</section>

	</c:param>
</c:import>