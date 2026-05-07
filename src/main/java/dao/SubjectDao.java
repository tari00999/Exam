package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    private String baseSql = "select * from subject where school_cd = ?";

    public List<Subject> filter(School school) throws Exception {

        List<Subject> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String order = " order by cd asc";

        try {
            statement = connection.prepareStatement(baseSql + order);

            // school_cd をバインド
            statement.setString(1, school.getCd());

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject();

                // DBカラム → Bean
                subject.setCd(resultSet.getString("cd"));
                subject.setName(resultSet.getString("name"));

                // 引数で受け取ったSchoolをそのままセット
                subject.setSchool(school);

                list.add(subject);
            }

        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // 単体取得（必要なら）
    public Subject get(String cd, School school) throws Exception {

        Subject subject = null;

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                "select * from subject where cd = ? and school_cd = ?"
            );

            statement.setString(1, cd);
            statement.setString(2, school.getCd());

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                subject = new Subject();

                subject.setCd(resultSet.getString("cd"));
                subject.setName(resultSet.getString("name"));
                subject.setSchool(school);
            }

        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return subject;
    }
    
    public boolean save(Subject subject) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            Subject old = get(subject.getCd(), subject.getSchool());

            if (old == null) {
                // INSERT
                statement = connection.prepareStatement(
                    "insert into subject(school_cd, cd, name) values(?, ?, ?)"
                );

                statement.setString(1, subject.getSchool().getCd());
                statement.setString(2, subject.getCd());
                statement.setString(3, subject.getName());

            } else {
                // UPDATE
                statement = connection.prepareStatement(
                    "update subject set name = ? where cd = ? and school_cd = ?"
                );

                statement.setString(1, subject.getName());
                statement.setString(2, subject.getCd());
                statement.setString(3, subject.getSchool().getCd());
            }

            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
    }
    
    public boolean delete(Subject subject) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "delete from subject where cd = ? and school_cd = ?"
            );

            statement.setString(1, subject.getCd());
            statement.setString(2, subject.getSchool().getCd());

            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
    }
}

