package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        String subjectCd = req.getParameter("subject_cd");
        String noStr = req.getParameter("no");
        String classNum = req.getParameter("class_num");

        String[] studentNos = req.getParameterValues("student_no");
        String[] scores = req.getParameterValues("score");

        Map<String, String> errors = new HashMap<>();

        if (subjectCd == null || subjectCd.isEmpty()) {
            errors.put("subject", "科目未選択");
        }

        int no = 0;
        try {
            no = Integer.parseInt(noStr);
        } catch (Exception e) {
            errors.put("no", "回数不正");
        }

        if (studentNos == null || scores == null) {
            errors.put("data", "データなし");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        TestDao dao = new TestDao();
        List<Test> list = new ArrayList<>();

        for (int i = 0; i < studentNos.length; i++) {

            Test test = new Test();

            Student student = new Student();
            student.setNo(studentNos[i]);
            test.setStudent(student);

            Subject subject = new Subject();
            subject.setCd(subjectCd);
            test.setSubject(subject);

            test.setSchool(teacher.getSchool());

            test.setNo(no);
            test.setClassNum(classNum);

            Integer score = null;

            if (scores[i] != null && !scores[i].isEmpty()) {
                score = Integer.parseInt(scores[i]);
            }

            test.setPoint(score);

            list.add(test);
        }

        for (Test t : list) {
            dao.save(t);
        }

        req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    }
}