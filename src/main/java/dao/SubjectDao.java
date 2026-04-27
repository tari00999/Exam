package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao {

    // ===== 一覧取得 =====
    public List<Subject> findAll(School school) throws Exception {

        List<Subject> list = new ArrayList<>();

        Connection con = getConnection();

        String sql = "SELECT code, name FROM subject WHERE school = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, school.getCd());

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject s = new Subject();
            s.setCode(rs.getString("code"));
            s.setName(rs.getString("name"));
            list.add(s);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    // ===== 1件取得 =====
    public Subject get(String code, School school) throws Exception {

        Connection con = getConnection();

        String sql = "SELECT code, name FROM subject WHERE code = ? AND school = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, code);
        st.setString(2, school.getCd());

        ResultSet rs = st.executeQuery();

        Subject subject = null;

        if (rs.next()) {
            subject = new Subject();
            subject.setCode(rs.getString("code"));
            subject.setName(rs.getString("name"));
        }

        rs.close();
        st.close();
        con.close();

        return subject;
    }

    // ===== 登録 =====
    public void insert(Subject subject) throws Exception {

        Connection con = getConnection();

        String sql = "INSERT INTO subject (code, name, school) VALUES (?, ?, ?)";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, subject.getCode());
        st.setString(2, subject.getName());
        st.setString(3, subject.getSchool().getCd());

        st.executeUpdate();

        st.close();
        con.close();
    }

    // ===== 更新 =====
    public int update(Subject subject) throws Exception {

        Connection con = getConnection();

        String sql = "UPDATE subject SET name = ? WHERE code = ? AND school = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, subject.getName());
        st.setString(2, subject.getCode());
        st.setString(3, subject.getSchool().getCd());

        int count = st.executeUpdate();

        st.close();
        con.close();

        return count;
    }

    // ===== 削除 =====
    public int delete(String code, School school) throws Exception {

        Connection con = getConnection();

        String sql = "DELETE FROM subject WHERE code = ? AND school = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, code);
        st.setString(2, school.getCd());

        int count = st.executeUpdate();

        st.close();
        con.close();

        return count;
    }

    // ===== DB接続 =====
    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/yourdb",
            "user",
            "password"
        );
    }
}