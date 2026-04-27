<%-- 科目削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp" >
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="content">
        <section>
            <h2 class="h3 mb-3 bg-secondary bg-opacity-10 py-2 px-4">
                科目削除
            </h2>

            <p>以下の科目を削除しますか？</p>

            <%-- 表示用 --%>
            <div class="mx-auto py-2">
                <label>科目コード</label><br>
                <span class="ps-3">${code}</span>
            </div>

            <div class="mx-auto py-2">
                <label>科目名</label><br>
                <span class="ps-3">${name}</span>
            </div>

            <%-- 削除実行フォーム --%>
            <form action="SubjectDeleteExecute.action" method="post">

                <%-- codeを送る --%>
                <input type="hidden" name="code" value="${code}" />

                <div class="mx-auto py-2">
                    <button class="btn btn-danger">
                        削除
                    </button>
                </div>
            </form>

            <a href="SubjectList.action">戻る</a>
        </section>
    </c:param>
</c:import>