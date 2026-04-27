package scoremanager.main;

import bean.School;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        // パラメータ取得
        String code = request.getParameter("code");

        // ===== 入力チェック =====
        if (code == null || code.isEmpty()) {
            request.setAttribute("error", "科目コードが指定されていません");
            return "subject-delete.jsp"; // 確認画面に戻す
        }

        // ===== 削除処理 =====
        SubjectDao dao = new SubjectDao();
        int count = dao.delete(code, school);

        // ===== 結果チェック =====
        if (count == 0) {
            request.setAttribute("error", "削除に失敗しました");
            return "subject-delete.jsp";
        }

        // 成功
        return "subject-delete-done.jsp";
    }
}