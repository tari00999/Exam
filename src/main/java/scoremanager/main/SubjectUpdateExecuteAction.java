package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        // パラメータ取得
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String creditStr = request.getParameter("credit");

        // ===== 入力チェック =====
        if (code == null || code.isEmpty() ||
            name == null || name.isEmpty() ||
            creditStr == null || creditStr.isEmpty()) {

            request.setAttribute("error", "未入力の項目があります");
            return "subject-update.jsp";
        }

        int credit;
        try {
            credit = Integer.parseInt(creditStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "単位は数値で入力してください");
            return "subject-update.jsp";
        }

        // Subject作成
        Subject subject = new Subject();
        subject.setCode(code);
        subject.setName(name);
        subject.setCredit(credit);
        subject.setSchool(school);

        // 更新処理
        SubjectDao dao = new SubjectDao();
        int count = dao.update(subject);

        // 更新結果チェック
        if (count == 0) {
            request.setAttribute("error", "更新に失敗しました");
            return "subject-update.jsp";
        }

        // 成功
        return "subject-update-done.jsp";
    }
}