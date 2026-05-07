package scoremanager.main;

import bean.ClassNum;
import bean.School;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassNumDeleteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        // セッション取得
        HttpSession session =
            request.getSession();

        // 学校情報取得
        School school =
            (School)session.getAttribute("school");

        // パラメータ取得
        String classNum =
            request.getParameter("classNum");

        // DAO
        ClassNumDao dao =
            new ClassNumDao();

        // 1件取得
        ClassNum c =
            dao.get(classNum, school);

        // requestへセット
        request.setAttribute(
            "classNum",
            c
        );

        // JSPへフォワード
        request.getRequestDispatcher(
            "class_delete.jsp"
        ).forward(request, response);
    }
}