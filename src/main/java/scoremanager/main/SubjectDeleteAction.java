package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            request.setAttribute("error", "科目コードが指定されていません");
            request.getRequestDispatcher("subject_list.jsp").forward(request, response);
            return;
        }

        SubjectDao dao = new SubjectDao();

        // ★ POSTなら削除実行
        if ("POST".equalsIgnoreCase(request.getMethod())) {

            int count = dao.delete(code, school);

            if (count == 0) {
                request.setAttribute("error", "削除に失敗しました");
                request.getRequestDispatcher("subject_list.jsp").forward(request, response);
                return;
            }

            request.getRequestDispatcher("subject_delete_done.jsp").forward(request, response);
            return;
        }

        // ★ GETなら確認画面表示（←ここが重要）
        Subject subject = dao.get(code, school);

        if (subject == null) {
            request.setAttribute("error", "科目が存在しません");
            request.getRequestDispatcher("subject_list.jsp").forward(request, response);
            return;
        }

        // JSPに渡す
        request.setAttribute("code", subject.getCode());
        request.setAttribute("name", subject.getName());

        request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
    }
}