package scoremanager.main;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Teacher;
import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        SubjectDao dao = new SubjectDao();

        List<Subject> subjects = dao.findAll(teacher.getSchool());

        req.setAttribute("subjects", subjects);

        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}