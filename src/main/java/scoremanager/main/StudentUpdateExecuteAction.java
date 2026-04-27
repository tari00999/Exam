package scoremanager.main;

import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // パラメータ取得
        String entYearStr = req.getParameter("ent_year");
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String classNum = req.getParameter("class_num");
        String isAttendStr = req.getParameter("is_attend");

        // ===== 入力チェック =====
        if (entYearStr == null || entYearStr.isEmpty() ||
            no == null || no.isEmpty() ||
            name == null || name.isEmpty() ||
            classNum == null || classNum.isEmpty()) {

            req.setAttribute("error", "未入力の項目があります");
            return "student_update.jsp";
        }

        int entYear;
        try {
            entYear = Integer.parseInt(entYearStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "入学年度は数値で入力してください");
            return "student_update.jsp";
        }

        boolean isAttend = (isAttendStr != null);

        // Student作成
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);

        // 更新処理
        StudentDao dao = new StudentDao();
        dao.save(student);

        // 完了画面へ
        return "student_update_done.jsp";
    }
}