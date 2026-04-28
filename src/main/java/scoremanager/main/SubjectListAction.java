package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログイン
        if (teacher == null) {
            res.sendRedirect("Login.action"); // forwardより安全
            return;
        }

        SubjectDao dao = new SubjectDao();
        List<Subject> subjects = null;

        try {
            subjects = dao.findAll(teacher.getSchool());
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        if (subjects == null) {
            subjects = new ArrayList<>();
        }

        // 必ずセット（null禁止）
        req.setAttribute("subjects", subjects);

        // JSPへ
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}