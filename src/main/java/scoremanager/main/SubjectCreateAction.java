package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        // ===== パラメータ取得 =====
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String creditStr = request.getParameter("credit");

        // ===== 入力チェック =====
        if (code == null || code.isEmpty() ||
            name == null || name.isEmpty() ||
            creditStr == null || creditStr.isEmpty()) {

            request.setAttribute("error", "未入力の項目があります");
            return "subject-create.jsp";
        }

        int credit;
        try {
            credit = Integer.parseInt(creditStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "単位は数値で入力してください");
            return "subject-create.jsp";
        }

        // ===== Subject作成 =====
        Subject subject = new Subject();
        subject.setCode(code);
        subject.setName(name);
        subject.setCredit(credit);
        subject.setSchool(school);

        // ===== DB登録 =====
        SubjectDao dao = new SubjectDao();
        dao.insert(subject);

        // ===== 完了画面 =====
        return "subject-create-done.jsp";
    }
}