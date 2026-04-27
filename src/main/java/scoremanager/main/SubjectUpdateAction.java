package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // セッション取得
        HttpSession session = request.getSession();

        // 学校情報取得
        School school = (School) session.getAttribute("school");

        // パラメータ取得
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        int credit = Integer.parseInt(request.getParameter("credit"));

        // Subjectオブジェクト作成
        Subject subject = new Subject();
        subject.setCode(code);
        subject.setName(name);
        subject.setCredit(credit);
        subject.setSchool(school);

        // DAOで更新
        SubjectDao dao = new SubjectDao();
        dao.update(subject);

        // 完了画面へ
        return;
    }
}