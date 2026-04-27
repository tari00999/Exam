package scoremanager.main;

import bean.Score;
import dao.ScoreDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ScoreUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // パラメータ取得
        String student_no = req.getParameter("student_no");
        String subject = req.getParameter("subject");

        // DBから取得
        ScoreDao scoreDao = new ScoreDao();
        Score score = scoreDao.get(student_no, subject);

        // JSPに渡す
        req.setAttribute("score", score);

        // 画面表示
        req.getRequestDispatcher("score_update.jsp").forward(req, res);
    }
}