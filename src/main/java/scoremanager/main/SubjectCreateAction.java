package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // DAO初期化
        SubjectDao subjectDao = new SubjectDao();

        // 学校コードをもとに科目一覧取得
        List<Subject> subjects = subjectDao.filter(teacher.getSchool());

        // リクエストスコープにセット
        req.setAttribute("subjects_set", subjects);

        // JSPへフォワード
        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
}