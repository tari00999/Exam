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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        String code = request.getParameter("code");
        String name = request.getParameter("name");

        // 入力チェック（あると安全）
        if (code == null || code.isEmpty() ||
            name == null || name.isEmpty()) {

            request.setAttribute("error", "未入力の項目があります");
            request.getRequestDispatcher("subject_create.jsp").forward(request, response);
            return;
        }

        Subject subject = new Subject();
        subject.setCode(code);
        subject.setName(name);
        subject.setSchool(school);

        SubjectDao dao = new SubjectDao();
        dao.insert(subject);

        request.getRequestDispatcher("subject_create_done.jsp").forward(request, response);
    }
}