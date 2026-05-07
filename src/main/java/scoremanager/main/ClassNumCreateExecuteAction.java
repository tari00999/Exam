package scoremanager.main;

import bean.ClassNum;
import bean.School;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassNumCreateExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        // セッション取得
        HttpSession session =
            request.getSession();

        // 学校情報取得
        School school =
            (School)session.getAttribute("school");

        // パラメータ取得
        String classNum =
            request.getParameter("classNum");

        // Bean作成
        ClassNum c = new ClassNum();

        c.setClassNum(classNum);
        c.setSchool(school);

        // DAO
        ClassNumDao dao =
            new ClassNumDao();

        // 重複チェック
        ClassNum old =
            dao.get(classNum, school);

        if (old == null) {

            // 登録
            boolean result =
                dao.save(c);

            if (result) {

                request.setAttribute(
                    "message",
                    "クラスを登録しました。"
                );

            } else {

                request.setAttribute(
                    "error",
                    "登録に失敗しました。"
                );
            }

        } else {

            request.setAttribute(
                "error",
                "そのクラスは既に存在しています。"
            );
        }

        // 一覧へ戻る
        request.getRequestDispatcher(
            "ClassNumList.action"
        ).forward(request, response);
    }
}