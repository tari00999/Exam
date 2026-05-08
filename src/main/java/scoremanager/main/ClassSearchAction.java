package scoremanager.main;

import java.util.List;

import bean.ClassNum;
import bean.School;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassSearchAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // セッション取得
        HttpSession session = request.getSession();

        // 学校情報取得
        School school = (School) session.getAttribute("school");

        // 検索条件取得
        String keyword = request.getParameter("keyword");

        // DAO
        ClassNumDao dao = new ClassNumDao();

        // 検索結果
        List<ClassNum> list;

        // 条件分岐
        if (keyword == null || keyword.isEmpty()) {

            // 全件取得
            list = dao.findAll(school);

        } else {

            // 検索
            list = dao.search(keyword, school);
        }

        // JSPへ渡す
        request.setAttribute("class_list", list);
        request.setAttribute("keyword", keyword);

        // JSPへフォワード
        request.getRequestDispatcher("class_search.jsp")
               .forward(request, response);
    }
}