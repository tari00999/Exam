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

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // =========================
        // 入力取得
        // =========================
        String studentNo = req.getParameter("student_no");

        Map<String, String> errors = new HashMap<>();

        // =========================
        // 入力チェック
        // =========================
        if (studentNo == null || studentNo.isEmpty()) {
            errors.put("student_no", "このフィールドを入力してください");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        // =========================
        // DB検索
        // =========================
        TestDao dao = new TestDao();

        List<Test> list = dao.filterByStudent(
                teacher.getSchool(),
                studentNo
        );

        // データなし
        if (list == null || list.isEmpty()) {
        	errors.put("nodata", "成績情報が存在しませんでした");
            req.setAttribute("errors", errors);
        }

        // =========================
        // 画面へ
        // =========================
        req.setAttribute("tests", list);
        req.setAttribute("student_no", studentNo);

        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}