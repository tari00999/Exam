package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ取得
        String code = req.getParameter("code");

        // DAO
        SubjectDao subjectDao = new SubjectDao();

        // DBから取得（学校で絞るのが重要）
        Subject subject = subjectDao.get(code, teacher.getSchool());

        // 見つからない場合
        if (subject == null) {
            req.setAttribute("error", "科目が見つかりません");
            req.getRequestDispatcher("SubjectList.action").forward(req, res);
            return;
        }

        // JSPに渡す
        req.setAttribute("code", subject.getCd());
        req.setAttribute("name", subject.getName());

        // フォワード
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}