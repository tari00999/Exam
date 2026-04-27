<%-- 科目登録JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
			<form action="SubjectCreateExecute.action" method="post">
			
			<%-- 科目コード --%>
				<div>
					<label for="code">科目コード</label><br>
					 <input class="form-control"
                        type="text"
                        id="code"
                        name="code"
                        value="${code}"
                        required
                        maxlength="10"
                        placeholder="科目コードを入力してください" />
				</div>
				<div class="mt-2 text-warning">
                    ${errors.get("code")}
                </div>
                
            <%-- 科目名 --%> 
				<div>
                    <label for="name">科目名</label><br>
                    <input class="form-control"
                        type="text"
                        id="name"
                        name="name"
                        value="${name}"
                        required
                        maxlength="30"
                        placeholder="科目名を入力してください" />
                </div>
                <div class="mt-2 text-warning">
                    ${errors.get("name")}
                </div>
                
             <%-- ボタン --%>  
             	<div class="mx-auto py-2">
                    <button class="btn btn-secondary" id="create-button">
                        登録して終了
                    </button>
                </div> 
                
			</form>
			<a href="SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>