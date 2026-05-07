package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // =========================
        // パラメータ取得
        // =========================
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");

        Map<String, String> errors = new HashMap<>();

        // =========================
        // 入力チェック
        // =========================
        if (entYearStr == null || entYearStr.isEmpty()) {
            errors.put("ent_year", "入学年度を選択してください");
        }

        if (classNum == null || classNum.isEmpty()) {
            errors.put("class_num", "クラスを選択してください");
        }

        if (subjectCd == null || subjectCd.isEmpty()) {
            errors.put("subject_cd", "科目を選択してください");
        }

        int entYear = 0;
        if (errors.isEmpty()) {
            entYear = Integer.parseInt(entYearStr);
        }

        // =========================
        // エラー時
        // =========================
        if (!errors.isEmpty()) {

            req.setAttribute("errors", errors);

            // 再表示用
            req.setAttribute("ent_year", entYearStr);
            req.setAttribute("class_num", classNum);
            req.setAttribute("subject_cd", subjectCd);

            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        // =========================
        // DB検索
        // =========================
        TestDao dao = new TestDao();

        List<Test> list = dao.filter(
                teacher.getSchool(),
                classNum,
                entYear,
                subjectCd,
                1 // ←回数（固定 or 後で拡張）
        );

        // =========================
        // データなし
        // =========================
        if (list == null || list.isEmpty()) {

            errors.put("nodata", "該当する成績がありません");

            req.setAttribute("errors", errors);
            req.setAttribute("ent_year", entYearStr);
            req.setAttribute("class_num", classNum);
            req.setAttribute("subject_cd", subjectCd);

            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        // =========================
        // 正常：表示用データ
        // =========================
        req.setAttribute("tests", list);

        // 再表示用（重要）
        req.setAttribute("ent_year", entYearStr);
        req.setAttribute("class_num", classNum);
        req.setAttribute("subject_cd", subjectCd);

        // =========================
        // JSPへ
        // =========================
        req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
    }
}