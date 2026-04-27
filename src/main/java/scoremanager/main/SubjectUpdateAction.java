package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        String code = request.getParameter("code");

        // チェック（あると安心）
        if (code == null || code.isEmpty()) {
            request.setAttribute("error", "科目コードが指定されていません");
            request.getRequestDispatcher("subject_list.jsp").forward(request, response);
            return;
        }

        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(code, school); // ※このメソッド必要

        request.setAttribute("subject", subject);

        // JSPへ
        request.getRequestDispatcher("subject_update.jsp").forward(request, response);
    }
}