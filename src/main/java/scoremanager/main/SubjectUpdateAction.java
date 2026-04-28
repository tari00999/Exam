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

        // ログインチェック
        if (teacher == null) {
            res.sendRedirect("Login.action");
            return;
        }

        String code = req.getParameter("code");

        // パラメータチェック
        if (code == null || code.isEmpty()) {
            res.sendRedirect("SubjectList.action");
            return;
        }

        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(code, teacher.getSchool());

        // データなし対策
        if (subject == null) {
            res.sendRedirect("SubjectList.action");
            return;
        }

        // JSPに渡す
        req.setAttribute("code", subject.getCode());
        req.setAttribute("name", subject.getName());

        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}