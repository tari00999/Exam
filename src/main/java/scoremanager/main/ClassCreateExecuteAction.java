package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // セッション取得
        HttpSession session = req.getSession();

        // ログインユーザー取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // DAO
        ClassNumDao classNumDao = new ClassNumDao();

        // 入力値取得
        String class_num = req.getParameter("class_num");

        // エラー格納
        Map<String, String> errors = new HashMap<>();

        // ClassNumインスタンス
        ClassNum classNum = new ClassNum();

        // -----------------------
        // バリデーション
        // -----------------------

        // 未入力チェック
        if (class_num == null || class_num.isEmpty()) {

            errors.put("class_num",
                    "クラス番号を入力してください");
        }

        // 重複チェック
        if (errors.isEmpty()) {

            if (classNumDao.get(
                    class_num,
                    teacher.getSchool()) != null) {

                errors.put("class_num",
                        "そのクラス番号は既に登録されています");
            }
        }

        // -----------------------
        // 登録処理
        // -----------------------
        if (errors.isEmpty()) {

            classNum.setClass_num(class_num);

            classNum.setSchool(teacher.getSchool());

            classNumDao.save(classNum);
        }

        // -----------------------
        // リクエストに戻す
        // -----------------------
        req.setAttribute("class_num", class_num);

        req.setAttribute("errors", errors);

        // -----------------------
        // フォワード
        // -----------------------
        if (errors.isEmpty()) {

            req.getRequestDispatcher("class_create_done.jsp")
                    .forward(req, res);

        } else {

            req.getRequestDispatcher("class_create.jsp")
                    .forward(req, res);
        }
    }
}