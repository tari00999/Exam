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
	    int credit = Integer.parseInt(request.getParameter("credit"));

	    Subject subject = new Subject();
	    subject.setCode(code);
	    subject.setName(name);
	    subject.setCredit(credit);
	    subject.setSchool(school);

	    SubjectDao dao = new SubjectDao();
	    dao.insert(subject);

	    // フォワードで遷移
	    request.getRequestDispatcher("subject-create-done.jsp").forward(request, response);
	}
}