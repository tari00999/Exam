package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // =========================
        // セッション取得
        // =========================
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログイン対策
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // =========================
        // DAO
        // =========================
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        StudentDao studentDao = new StudentDao();

        // =========================
        // 初期データ取得
        // =========================
        List<String> classList = classNumDao.filter(teacher.getSchool());
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

        req.setAttribute("class_num_set", classList);
        req.setAttribute("subject_set", subjectList);

        // =========================
        // 入学年度リスト
        // =========================
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
        String noStr = req.getParameter("no");

        // =========================
        // 検索ボタン押下判定
        // =========================
        boolean isSearch =
                entYearStr != null ||
                classNum != null ||
                subjectCd != null ||
                noStr != null;

        // =========================
        // 検索処理
        // =========================
        if (isSearch) {

            // 再表示用
            req.setAttribute("ent_year", entYearStr);
            req.setAttribute("class_num", classNum);
            req.setAttribute("subject_cd", subjectCd);
            req.setAttribute("no", noStr);

            // 入力チェック
            if (entYearStr == null || entYearStr.isEmpty()
                    || classNum == null || classNum.isEmpty()
                    || subjectCd == null || subjectCd.isEmpty()
                    || noStr == null || noStr.isEmpty()) {

                req.setAttribute(
                        "message",
                        "入学年度とクラスと科目と回数を選択してください");

            } else {

                // 型変換
                int entYear = Integer.parseInt(entYearStr);

                // 学生検索
                List<Student> students = studentDao.filter(
                        teacher.getSchool(),
                        entYear,
                        classNum,
                        true);

                // 学生存在チェック
                if (students == null || students.isEmpty()) {

                    req.setAttribute(
                            "message",
                            "学生情報が存在しませんでした");

                } else {

                    req.setAttribute("students", students);
                }
            }
        }

        // =========================
        // JSPへフォワード
        // =========================
        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}
