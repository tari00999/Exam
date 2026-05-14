package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // =========================
        // DAO
        // =========================
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao();

        // =========================
        // プルダウン用データ
        // =========================
        List<String> classList = classNumDao.filter(teacher.getSchool());
        List<?> subjectList = subjectDao.filter(teacher.getSchool());

        req.setAttribute("class_num_set", classList);
        req.setAttribute("subject_set", subjectList);

        // 年度リスト
        LocalDate today = LocalDate.now();
        int year = today.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }
        req.setAttribute("ent_year_set", entYearSet);

        // =========================
        // パラメータ取得
        // =========================
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");

        Map<String, String> errors = new HashMap<>();

        // =========================
        // 検索ボタン押下判定
        // =========================
        if (entYearStr != null || classNum != null || subjectCd != null) {

            // 必須チェック
            if (entYearStr == null || entYearStr.isEmpty()) {
                errors.put("ent_year", "入学年度を選択してください");
            }
            if (classNum == null || classNum.isEmpty()) {
                errors.put("class_num", "クラスを選択してください");
            }
            if (subjectCd == null || subjectCd.isEmpty()) {
                errors.put("subject_cd", "科目を選択してください");
            }

            // エラーがある場合
            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
            } else {

                int entYear = Integer.parseInt(entYearStr);

                // =========================
                // 成績検索
                // =========================
                List<Test> list = testDao.filter(
                        teacher.getSchool(),
                        classNum,
                        entYear,
                        subjectCd,
                        1 // ←回数固定（必要なら変更）
                );
                
                req.setAttribute("tests", list);

                // データなし
                if (list == null || list.isEmpty()) {
                    errors.put("nodata", "該当する成績がありません");
                    req.setAttribute("errors", errors);
                } else {
                    req.setAttribute("tests", list);
                }

                // 再表示用
                req.setAttribute("ent_year", entYearStr);
                req.setAttribute("class_num", classNum);
                req.setAttribute("subject_cd", subjectCd);
            }
        }

        // =========================
        // JSPへ
        // =========================
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}