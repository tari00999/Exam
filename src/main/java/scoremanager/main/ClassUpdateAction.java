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
		HttpSession session =req.getSession();
		
		//学生情報
		School school=(School)session.getAttribute("school");
		
		//パラメータ
		String classNum=req.getParameter("class_num");
		
		//DAO
		ClassNumDao dao = new ClassNumDao();
		//データ取得
		bean.ClassNum c = dao.get(classNum, school);
		
		//jspへ渡す
		req.setAttribute("class_data", c);
		
		//更新画面
		req.getRequestDispatcher("class_update.jsp")
		   .forward(req, res);
	}

}
