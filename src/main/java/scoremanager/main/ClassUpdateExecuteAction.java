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

public class ClassUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req,
                        HttpServletResponse res)
            throws Exception {

        // セッション取得
        HttpSession session = req.getSession();

        Teacher teacher =
                (Teacher) session.getAttribute("user");

        // パラメータ取得
        String class_num =
                req.getParameter("class_num");

        String new_class_num =
                req.getParameter("new_class_num");

        // DAO
        ClassNumDao classNumDao =
                new ClassNumDao();

        // エラー格納
        Map<String, String> errors =
                new HashMap<>();

        // -----------------------
        // バリデーション
        // -----------------------

        // 未入力チェック
        if (new_class_num == null
                || new_class_num.isEmpty()) {

            errors.put(
                    "new_class_num",
                    "クラス番号を入力してください");
        }

        // 重複チェック
        if (errors.isEmpty()) {

            ClassNum exists =
                    classNumDao.get(
                            new_class_num,
                            teacher.getSchool());

            // 自分自身以外で重複
            if (exists != null
                    && !class_num.equals(new_class_num)) {

                errors.put(
                        "new_class_num",
                        "そのクラス番号は既に存在します");
            }
        }

        // -----------------------
        // 更新処理
        // -----------------------
        if (errors.isEmpty()) {

            // 元データ取得
            ClassNum classNum =
                    classNumDao.get(
                            class_num,
                            teacher.getSchool());

            // 更新
            classNumDao.save(
                    classNum,
                    new_class_num);
        }

        // -----------------------
        // リクエストに戻す
        // -----------------------
        req.setAttribute(
                "class_num",
                class_num);

        req.setAttribute(
                "new_class_num",
                new_class_num);

        req.setAttribute(
                "errors",
                errors);

        // -----------------------
        // フォワード
        // -----------------------
        if (errors.isEmpty()) {

            req.getRequestDispatcher(
                    "class_update_done.jsp")
                    .forward(req, res);

        } else {

            req.getRequestDispatcher(
                    "class_update.jsp")
                    .forward(req, res);
        }
    }
}