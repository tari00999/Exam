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

        String code = request.getParameter("code");

        SubjectDao dao = new SubjectDao();

        // ===== 入力チェック =====
        if (code == null || code.isEmpty()) {
            request.setAttribute("error", "科目コードが指定されていません");
            request.getRequestDispatcher("subject_list.jsp").forward(request, response);
            return;
        }

        // ★ 再取得（エラー時表示用にも使う）
        Subject subject = dao.get(code, school);

        if (subject == null) {
            request.setAttribute("error", "科目が存在しません");
            request.getRequestDispatcher("subject_list.jsp").forward(request, response);
            return;
        }

        // ===== 削除処理 =====
        int count = dao.delete(code, school);

        // ===== 結果チェック =====
        if (count == 0) {
            request.setAttribute("error", "削除に失敗しました");

            // ★ JSP用に値を再セット（重要）
            request.setAttribute("code", subject.getCode());
            request.setAttribute("name", subject.getName());

            request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
            return;
        }

        // 成功
        request.getRequestDispatcher("subject_delete_done.jsp").forward(request, response);
    }
}