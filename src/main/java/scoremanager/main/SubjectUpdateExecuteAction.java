package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ取得
        String code = req.getParameter("code");
        String name = req.getParameter("name");

        // DAO
        SubjectDao subjectDao = new SubjectDao();

        // Subject作成
        Subject subject = new Subject();

        // セット
        subject.setCd(code);
        subject.setName(name);
        subject.setSchool(teacher.getSchool()); // ← これ重要！

        // 更新（saveでUPDATEされる）
        subjectDao.save(subject);

        // フォワード
        req.getRequestDispatcher("subject_update_done.jsp")
           .forward(req, res);
    }
}