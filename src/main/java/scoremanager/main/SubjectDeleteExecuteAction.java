package scoremanager.main;

import bean.Subject;   // ← 追加
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ取得
        String code = req.getParameter("code");

        // Subjectオブジェクトを作成
        Subject subject = new Subject();
        subject.setCd(code);
        subject.setSchool(teacher.getSchool());

        // DAO
        SubjectDao subjectDao = new SubjectDao();

        // 削除実行
        subjectDao.delete(subject);

        // 完了画面へ
        req.getRequestDispatcher("subject_delete_done.jsp")
           .forward(req, res);
    }
}
