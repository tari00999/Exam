package scoremanager.main;

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
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 安全チェック（おすすめ）
        if (teacher == null) {
            return "login.jsp";
        }

        SubjectDao dao = new SubjectDao();

        List<Subject> subjects = dao.findAll(teacher.getSchool());

        req.setAttribute("subjects", subjects);

        // JSPへ遷移
        return "subject_list.jsp";
    }
}