package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends {

    public List<Subject> findAll(School school) throws Exception {

        List<Subject> list = new ArrayList<>();

        Connection con = getConnection();

        String sql = "SELECT code, name, credit FROM subject WHERE school = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setNString(1, school);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject s = new Subject();
            s.setCode(rs.getString("code"));
            s.setName(rs.getString("name"));
            s.setCredit(rs.getInt("credit"));
            list.add(s);
        }

        st.close();
        con.close();

        return list;
    }

	private Connection getConnection() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}