<%-- クラス情報変更JSP --%>
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
    uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section>

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                クラス情報変更
            </h2>

            <form action="ClassUpdateExecute.action"
                  method="post">

                <%-- 変更前クラス番号(hidden) --%>
                <input type="hidden"
                       name="class_num"
                       value="${class_num}" />

                <%-- 現在のクラス番号表示 --%>
                <div class="mx-auto py-2">

                    <label>
                        現在のクラス番号
                    </label><br>

                    <input class="border border-0 ps-3"
                           type="text"
                           value="${class_num}"
                           readonly />

                </div>

                <%-- 新しいクラス番号 --%>
                <div class="mx-auto py-2">

                    <label for="new_class_num">
                        新しいクラス番号
                    </label><br>

                    <input class="form-control"
                           type="text"
                           id="new_class_num"
                           name="new_class_num"
                           value="${new_class_num}"
                           required
                           maxlength="10" />

                </div>

                <%-- エラー表示 --%>
                <div class="mt-2 text-warning">
                    ${errors.get("new_class_num")}
                </div>

                <%-- 更新ボタン --%>
                <div class="mx-auto py-2">

                    <input class="btn btn-primary"
                           type="submit"
                           value="変更" />

                </div>

            </form>

            <a href="ClassList.action">
                戻る
            </a>

        </section>

    </c:param>

</c:import>