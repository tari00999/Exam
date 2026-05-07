package scoremanager.main;

import java.util.List;

import bean.ClassNum;
import bean.School;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassNumListAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        HttpSession session = request.getSession();

        School school =
            (School)session.getAttribute("school");

        // DAO
        ClassNumDao dao = new ClassNumDao();

        // 一覧取得
        List<ClassNum> classList =
            dao.findAll(school);

        // requestへセット
        request.setAttribute(
            "classList",
            classList
        );

        // JSPへフォワード
        request.getRequestDispatcher(
            "class_list.jsp"
        ).forward(request, response);
    }
}