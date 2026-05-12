<%-- クラス削除JSP --%>
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
                クラス削除
            </h2>

            <p>以下のクラスを削除しますか？</p>

            <%-- クラス番号表示 --%>
            <div class="mx-auto py-2">
                <label>クラス番号</label><br>
                <span class="ps-3">${class_num}</span>
            </div>

            <%-- 削除実行フォーム --%>
            <form action="ClassDeleteExecute.action"
                  method="post">

                <%-- class_numを送る --%>
                <input type="hidden"
                       name="class_num"
                       value="${class_num}" />

                <div class="mx-auto py-2">
                    <button class="btn btn-danger">
                        削除
                    </button>
                </div>

            </form>

            <a href="ClassList.action">
                戻る
            </a>

        </section>

    </c:param>

</c:import>