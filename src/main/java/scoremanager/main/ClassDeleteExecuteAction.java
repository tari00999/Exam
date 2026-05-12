package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassDeleteExecuteAction extends Action {

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

        // 削除用オブジェクト作成
        ClassNum classNum = new ClassNum();
        classNum.setClass_num(class_num);
        classNum.setSchool(teacher.getSchool());

        // DAO
        ClassNumDao dao = new ClassNumDao();

        // 削除実行
        dao.delete(classNum);

        // 完了画面へ
        req.getRequestDispatcher("class_delete_done.jsp")
           .forward(req, res);
    }
}