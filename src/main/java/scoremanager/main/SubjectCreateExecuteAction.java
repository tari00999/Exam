package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインチェック
        if (teacher == null) {
            response.sendRedirect("Login.action");
            return;
        }

        // ★ 正しい取得方法
        School school = teacher.getSchool();

        String code = request.getParameter("code");
        String name = request.getParameter("name");

        Map<String, String> errors = new HashMap<>();

        // 入力チェック
        if (code == null || code.isEmpty()) {
            errors.put("code", "科目コードを入力してください");
        }
        if (name == null || name.isEmpty()) {
            errors.put("name", "科目名を入力してください");
        }

        // エラー
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.getRequestDispatcher("subject_create.jsp").forward(request, response);
            return;
        }

        // 登録
        Subject subject = new Subject();
        subject.setCode(code);
        subject.setName(name);
        subject.setSchool(school);

        SubjectDao dao = new SubjectDao();
        dao.insert(subject);

        request.getRequestDispatcher("subject_create_done.jsp").forward(request, response);
    }
}