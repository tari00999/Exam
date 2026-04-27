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

    public List<Subject> findAll(School school) throws Exception {

        List<Subject> list = new ArrayList<>();

        Connection con = getConnection();

        String sql = "SELECT code, name, credit FROM subject WHERE school = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, school.getCd()); // ←ここ重要

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject s = new Subject();
            s.setCode(rs.getString("code"));
            s.setName(rs.getString("name"));
            s.setCredit(rs.getInt("credit"));
            list.add(s);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/yourdb",
            "user",
            "password"
        );
    }

	public void insert(Subject subject) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}