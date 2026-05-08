package scoremanager.main;

import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // セッションからログインユーザー取得
        HttpSession session = req.getSession();

        Teacher teacher =
                (Teacher) session.getAttribute("user");

        // DAO
        ClassNumDao classNumDao = new ClassNumDao();

        // クラス一覧取得
        List<String> classList =
                classNumDao.filter(teacher.getSchool());

        // リクエストにセット
        req.setAttribute("classes_set", classList);

        // フォワード
        req.getRequestDispatcher("class_list.jsp")
                .forward(req, res);
    }
}