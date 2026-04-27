package scoremanager.main;

import bean.School;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        // パラメータ取得（削除対象）
        String code = request.getParameter("code");

        // 簡単なチェック
        if (code == null || code.isEmpty()) {
            request.setAttribute("error", "科目コードが指定されていません");
            return "subject-list.jsp";
        }

        // 削除処理
        SubjectDao dao = new SubjectDao();
        int count = dao.delete(code, school);

        // 結果チェック
        if (count == 0) {
            request.setAttribute("error", "削除に失敗しました");
            return "subject-list.jsp";
        }

        // 成功
        return "subject-delete-done.jsp";
    }
}