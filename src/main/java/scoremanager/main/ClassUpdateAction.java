package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req,
                        HttpServletResponse res)
            throws Exception {

        // セッション取得
        HttpSession session = req.getSession();

        Teacher teacher =
                (Teacher) session.getAttribute("user");

        // パラメータ取得
        String class_num =
                req.getParameter("class_num");

        // DAO
        ClassNumDao classNumDao =
                new ClassNumDao();

        // DBからクラス取得
        ClassNum classNum =
                classNumDao.get(
                        class_num,
                        teacher.getSchool());

        // null対策
        if (classNum == null) {

            req.setAttribute(
                    "error",
                    "クラス情報が見つかりません");

            req.getRequestDispatcher("class_list.jsp")
                    .forward(req, res);

            return;
        }

        // リクエストにセット
        req.setAttribute(
                "class_num",
                classNum.getClass_num());

        // フォワード
        req.getRequestDispatcher("class_update.jsp")
                .forward(req, res);
    }
}