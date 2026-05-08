package scoremanager.main;

import bean.School;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
		//セッション
		HttpSession session =request.getSession();
		
		//学生情報
		School school=(school)session.getAttribute("school");
		
		//パラメータ
		Stiring classNum=request.getParameter("class_num");
		
		//DAO
		ClassNumDao dao=dao.get(classNum, school);
		
		//jspへ渡す
		
	}

}
