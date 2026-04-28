package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        if (school == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // POSTチェック
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            response.sendRedirect("SubjectList.action");
            return;
        }

        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            request.setAttribute("error", "科目コードが指定されていません");
            request.getRequestDispatcher("subject_list.jsp").forward(request, response);
            return;
        }

        SubjectDao dao = new SubjectDao();

        Subject subject = dao.get(code, school);

        if (subject == null) {
            request.setAttribute("error", "科目が存在しません");
            request.getRequestDispatcher("subject_list.jsp").forward(request, response);
            return;
        }

        int count = dao.delete(code, school);

        if (count == 0) {
            request.setAttribute("error", "削除に失敗しました");
            request.setAttribute("code", subject.getCode());
            request.setAttribute("name", subject.getName());
            request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("subject_delete_done.jsp").forward(request, response);
    }
}