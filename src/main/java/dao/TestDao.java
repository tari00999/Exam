package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    // ==================================================
    // ① 成績参照（登録済のみ）
    // ==================================================
    public List<Test> filter(School school, String classNum,
                             int entYear, String subjectCd, int num) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection con = getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            String sql =
                "SELECT t.STUDENT_NO, t.SUBJECT_CD, t.SCHOOL_CD, t.NO, t.POINT, t.CLASS_NUM, " +
                "s.NAME AS STUDENT_NAME " +
                "FROM TEST t " +
                "JOIN STUDENT s ON t.STUDENT_NO = s.NO " +
                "WHERE s.SCHOOL_CD = ? " +
                "AND s.CLASS_NUM = ? " +
                "AND s.ENT_YEAR = ? " +
                "AND t.SUBJECT_CD = ? " +
                "AND t.NO = ? " +
                "ORDER BY s.NO";

            st = con.prepareStatement(sql);

            st.setString(1, school.getCd());
            st.setString(2, classNum);
            st.setInt(3, entYear);
            st.setString(4, subjectCd);
            st.setInt(5, num);

            rs = st.executeQuery();

            while (rs.next()) {

                Test test = new Test();

                Student student = new Student();
                student.setNo(rs.getString("STUDENT_NO"));
                student.setName(rs.getString("STUDENT_NAME"));
                test.setStudent(student);

                Subject subject = new Subject();
                subject.setCd(rs.getString("SUBJECT_CD"));
                test.setSubject(subject);

                School sc = new School();
                sc.setCd(rs.getString("SCHOOL_CD"));
                test.setSchool(sc);

                test.setNo(rs.getInt("NO"));
                test.setPoint((Integer) rs.getObject("POINT"));
                test.setClassNum(rs.getString("CLASS_NUM"));

                list.add(test);
            }

        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return list;
    }

    // ==================================================
    // ② 成績登録用（全学生表示：未登録も出す）
    // ==================================================
    public List<Test> filterAll(School school, String classNum,
                                int entYear, String subjectCd, int num) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection con = getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            String sql =
                "SELECT s.NO AS STUDENT_NO, s.NAME AS STUDENT_NAME, " +
                "t.SUBJECT_CD, t.SCHOOL_CD, t.NO, t.POINT, t.CLASS_NUM " +
                "FROM STUDENT s " +
                "LEFT JOIN TEST t ON s.NO = t.STUDENT_NO " +
                "AND t.SUBJECT_CD = ? " +
                "AND t.NO = ? " +
                "AND t.SCHOOL_CD = ? " +
                "WHERE s.SCHOOL_CD = ? " +
                "AND s.CLASS_NUM = ? " +
                "AND s.ENT_YEAR = ? " +
                "ORDER BY s.NO";

            st = con.prepareStatement(sql);

            st.setString(1, subjectCd);
            st.setInt(2, num);
            st.setString(3, school.getCd());
            st.setString(4, school.getCd());
            st.setString(5, classNum);
            st.setInt(6, entYear);

            rs = st.executeQuery();

            while (rs.next()) {

                Test test = new Test();

                Student student = new Student();
                student.setNo(rs.getString("STUDENT_NO"));
                student.setName(rs.getString("STUDENT_NAME"));
                test.setStudent(student);

                Subject subject = new Subject();
                subject.setCd(subjectCd);
                test.setSubject(subject);

                test.setSchool(school);
                test.setNo(num);

                test.setPoint((Integer) rs.getObject("POINT")); // nullOK
                test.setClassNum(classNum);

                list.add(test);
            }

        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return list;
    }

    // ==================================================
    // ③ 保存（INSERT / UPDATE）
    // ==================================================
    public boolean save(Test test) throws Exception {

        Connection con = getConnection();
        PreparedStatement st = null;
        int count = 0;

        try {

            Test old = get(
                test.getStudent().getNo(),
                test.getSubject().getCd(),
                test.getSchool().getCd(),
                test.getNo()
            );

            if (old == null) {

                st = con.prepareStatement(
                    "INSERT INTO TEST " +
                    "(STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) " +
                    "VALUES (?, ?, ?, ?, ?, ?)"
                );

                st.setString(1, test.getStudent().getNo());
                st.setString(2, test.getSubject().getCd());
                st.setString(3, test.getSchool().getCd());
                st.setInt(4, test.getNo());
                st.setObject(5, test.getPoint());
                st.setString(6, test.getClassNum());

            } else {

                st = con.prepareStatement(
                    "UPDATE TEST SET POINT = ? " +
                    "WHERE STUDENT_NO = ? " +
                    "AND SUBJECT_CD = ? " +
                    "AND SCHOOL_CD = ? " +
                    "AND NO = ?"
                );

                st.setObject(1, test.getPoint());
                st.setString(2, test.getStudent().getNo());
                st.setString(3, test.getSubject().getCd());
                st.setString(4, test.getSchool().getCd());
                st.setInt(5, test.getNo());
            }

            count = st.executeUpdate();

        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return count > 0;
    }

    // ==================================================
    // ④ 単体取得
    // ==================================================
    public Test get(String studentNo, String subjectCd,
                    String schoolCd, int no) throws Exception {

        Test test = null;

        Connection con = getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            st = con.prepareStatement(
                "SELECT * FROM TEST " +
                "WHERE STUDENT_NO = ? " +
                "AND SUBJECT_CD = ? " +
                "AND SCHOOL_CD = ? " +
                "AND NO = ?"
            );

            st.setString(1, studentNo);
            st.setString(2, subjectCd);
            st.setString(3, schoolCd);
            st.setInt(4, no);

            rs = st.executeQuery();

            if (rs.next()) {

                test = new Test();

                Student student = new Student();
                student.setNo(rs.getString("STUDENT_NO"));
                test.setStudent(student);

                Subject subject = new Subject();
                subject.setCd(rs.getString("SUBJECT_CD"));
                test.setSubject(subject);

                School school = new School();
                school.setCd(rs.getString("SCHOOL_CD"));
                test.setSchool(school);

                test.setNo(rs.getInt("NO"));
                test.setPoint((Integer) rs.getObject("POINT"));
                test.setClassNum(rs.getString("CLASS_NUM"));
            }

        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return test;
    }
    
    public List<Test> filterByStudent(School school, String studentNo) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            String sql =
                "SELECT " +
                " t.STUDENT_NO, t.SUBJECT_CD, t.SCHOOL_CD, t.NO, t.POINT, t.CLASS_NUM, " +
                " s.NAME AS STUDENT_NAME " +
                "FROM TEST t " +
                "JOIN STUDENT s ON t.STUDENT_NO = s.NO " +
                "WHERE t.SCHOOL_CD = ? " +
                "AND t.STUDENT_NO = ? " +
                "ORDER BY t.SUBJECT_CD, t.NO";

            statement = connection.prepareStatement(sql);

            statement.setString(1, school.getCd());
            statement.setString(2, studentNo);

            rs = statement.executeQuery();

            while (rs.next()) {

                Test test = new Test();

                Student student = new Student();
                student.setNo(rs.getString("STUDENT_NO"));
                student.setName(rs.getString("STUDENT_NAME"));
                test.setStudent(student);

                Subject subject = new Subject();
                subject.setCd(rs.getString("SUBJECT_CD"));
                test.setSubject(subject);

                School sc = new School();
                sc.setCd(rs.getString("SCHOOL_CD"));
                test.setSchool(sc);

                test.setNo(rs.getInt("NO"));
                test.setPoint((Integer) rs.getObject("POINT"));
                test.setClassNum(rs.getString("CLASS_NUM"));

                list.add(test);
            }

        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }
}