package scoremanager.main;

import bean.School;
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

        String code = request.getParameter("code");

        // ===== 入力チェック =====
        if (code == null || code.isEmpty()) {
            request.setAttribute("error", "科目コードが指定されていません");
            request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
            return;
        }

        // ===== 削除処理 =====
        SubjectDao dao = new SubjectDao();
        int count = dao.delete(code, school);

        // ===== 結果チェック =====
        if (count == 0) {
            request.setAttribute("error", "削除に失敗しました");
            request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
            return;
        }

        // 成功
        request.getRequestDispatcher("subject_delete_done.jsp").forward(request, response);
    }
}