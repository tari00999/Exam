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
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからログインユーザー取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        

        // DAO
        SubjectDao subjectDao = new SubjectDao();

        // 科目一覧取得（学校単位）
        List<Subject> subjects = subjectDao.filter(teacher.getSchool());

        // リクエストにセット
        req.setAttribute("subjects_set", subjects);

        // フォワード
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}