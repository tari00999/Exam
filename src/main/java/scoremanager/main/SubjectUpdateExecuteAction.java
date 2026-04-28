package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        //  ログイン
        if (school == null) {
            response.sendRedirect("Login.action");
            return;
        }

        String code = request.getParameter("code");
        String name = request.getParameter("name");

        Map<String, String> errors = new HashMap<>();

        // ===== 入力チェック =====
        if (code == null || code.isEmpty()) {
            errors.put("code", "科目コードが未入力です");
        }
        if (name == null || name.isEmpty()) {
            errors.put("name", "科目名が未入力です");
        }

        // エラーがある場合
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);

            // ★ 入力値保持（超重要）
            request.setAttribute("code", code);
            request.setAttribute("name", name);

            request.getRequestDispatcher("subject_update.jsp").forward(request, response);
            return;
        }

        // Subject作成
        Subject subject = new Subject();
        subject.setCode(code);
        subject.setName(name);
        subject.setSchool(school);

        SubjectDao dao = new SubjectDao();
        int count = dao.update(subject);

        // 更新失敗
        if (count == 0) {

            errors.put("common", "更新に失敗しました");
            request.setAttribute("errors", errors);

            request.setAttribute("code", code);
            request.setAttribute("name", name);

            request.getRequestDispatcher("subject_update.jsp").forward(request, response);
            return;
        }

        // 成功
        request.getRequestDispatcher("subject_update_done.jsp").forward(request, response);
    }
}