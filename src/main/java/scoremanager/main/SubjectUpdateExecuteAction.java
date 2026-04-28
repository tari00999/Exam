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

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインチェック
        if (teacher == null) {
            response.sendRedirect("Login.action");
            return;
        }

        // ★ ここが重要
        School school = teacher.getSchool();

        String code = request.getParameter("code");
        String name = request.getParameter("name");

        Map<String, String> errors = new HashMap<>();

        if (code == null || code.isEmpty()) {
            errors.put("code", "科目コードが未入力です");
        }
        if (name == null || name.isEmpty()) {
            errors.put("name", "科目名が未入力です");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.getRequestDispatcher("subject_update.jsp").forward(request, response);
            return;
        }

        Subject subject = new Subject();
        subject.setCode(code);
        subject.setName(name);
        subject.setSchool(school);

        SubjectDao dao = new SubjectDao();
        int count = dao.update(subject);

        if (count == 0) {
            errors.put("common", "更新に失敗しました");
            request.setAttribute("errors", errors);
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.getRequestDispatcher("subject_update.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("subject_update_done.jsp").forward(request, response);
    }
}