package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassDeleteAction extends Action {

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
        ClassNumDao dao = new ClassNumDao();

        // DB検索
        ClassNum classNum =
            dao.get(class_num, teacher.getSchool());

        // 見つからない場合
        if (classNum == null) {

            req.setAttribute("error",
                             "クラス情報が見つかりません");

            req.getRequestDispatcher("ClassList.action")
               .forward(req, res);

            return;
        }

        // JSPへ渡す
        req.setAttribute("class_num",
                         classNum.getClass_num());

        // 削除確認画面へ
        req.getRequestDispatcher("class_delete.jsp")
           .forward(req, res);
    }
}
