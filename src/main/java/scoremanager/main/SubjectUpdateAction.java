package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログイン
        if (teacher == null) {
            res.sendRedirect("Login.action");
            return;
        }

        String code = req.getParameter("code");
        String name = req.getParameter("name");

        // 入力チェック
        if (code == null || code.isEmpty() ||
            name == null || name.isEmpty()) {

            // 値を戻す
            req.setAttribute("code", code);
            req.setAttribute("name", name);

            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }

        Subject subject = new Subject();
        subject.setCode(code);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        SubjectDao dao = new SubjectDao();
        dao.update(subject);

        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}