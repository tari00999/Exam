package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // DAO
        SubjectDao subjectDao = new SubjectDao();

        // 入力値
        String code = req.getParameter("code");
        String name = req.getParameter("name");

        // エラー格納
        Map<String, String> errors = new HashMap<>();

        // Subject作成
        Subject subject = new Subject();

        // -----------------------
        // バリデーション
        // -----------------------

        // 科目コード未入力
        if (code == null || code.isEmpty()) {
            errors.put("code", "科目コードを入力してください");
        }

        // 科目名未入力
        if (name == null || name.isEmpty()) {
            errors.put("name", "科目名を入力してください");
        }

        // 重複チェック（コード）
        if (errors.isEmpty()) {
            if (subjectDao.get(code, teacher.getSchool()) != null) {
                errors.put("code", "その科目コードは既に登録されています");
            }
        }

        // -----------------------
        // 登録処理
        // -----------------------
        if (errors.isEmpty()) {

            subject.setCd(code);
            subject.setName(name);
            subject.setSchool(teacher.getSchool());

            subjectDao.save(subject); 

        }

        // -----------------------
        // リクエストに戻す
        // -----------------------
        req.setAttribute("code", code);
        req.setAttribute("name", name);
        req.setAttribute("errors", errors);

        // -----------------------
        // フォワード
        // -----------------------
        if (errors.isEmpty()) {
            req.getRequestDispatcher("subject_create_done.jsp")
               .forward(req, res);
        } else {
            req.getRequestDispatcher("subject_create.jsp")
               .forward(req, res);
        }
    }
}