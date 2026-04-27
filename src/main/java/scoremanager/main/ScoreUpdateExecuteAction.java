package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Teacher;
import dao.ScoreDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ScoreUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		String student_no = "";
		String subject = "";
		String scoreStr = "";
		int score = 0;

		ScoreDao scoreDao = new ScoreDao();
		Map<String, String> errors = new HashMap<>();

		// パラメータ取得
		student_no = req.getParameter("student_no");
		subject = req.getParameter("subject");
		scoreStr = req.getParameter("score");

		// 数値変換
		if (scoreStr != null && !scoreStr.isEmpty()) {
			score = Integer.parseInt(scoreStr);
		}

		// 入力チェック
		if (student_no == null || student_no.isEmpty()) {
			errors.put("1", "学生番号を入力してください");
		} else if (subject == null || subject.isEmpty()) {
			errors.put("2", "科目を入力してください");
		} else if (scoreStr == null || scoreStr.isEmpty()) {
			errors.put("3", "点数を入力してください");
		} else {
			// DB更新
			scoreDao.update(student_no, subject, score);
		}

		// 値戻し
		req.setAttribute("student_no", student_no);
		req.setAttribute("subject", subject);
		req.setAttribute("score", scoreStr);

		// 画面遷移
		if (errors.isEmpty()) {
			req.getRequestDispatcher("score_update_done.jsp").forward(req, res);
		} else {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("ScoreUpdate.action").forward(req, res);
		}
	}
}