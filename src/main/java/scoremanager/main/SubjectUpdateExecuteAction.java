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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String creditStr = request.getParameter("credit");

        // ===== 入力チェック =====
        if (code == null || code.isEmpty() ||
            name == null || name.isEmpty() ||
            creditStr == null || creditStr.isEmpty()) {

            request.setAttribute("error", "未入力の項目があります");
            request.getRequestDispatcher("subject_update.jsp").forward(request, response);
            return;
        }

        int credit;
        try {
            credit = Integer.parseInt(creditStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "単位は数値で入力してください");
            request.getRequestDispatcher("subject_update.jsp").forward(request, response);
            return;
        }

        Subject subject = new Subject();
        subject.setCode(code);
        subject.setName(name);
        subject.setCredit(credit);
        subject.setSchool(school);

        SubjectDao dao = new SubjectDao();
        int count = dao.update(subject);

        if (count == 0) {
            request.setAttribute("error", "更新に失敗しました");
            request.getRequestDispatcher("subject_update.jsp").forward(request, response);
            return;
        }

        // 成功
        request.getRequestDispatcher("subject_update_done.jsp").forward(request, response);
    }
}