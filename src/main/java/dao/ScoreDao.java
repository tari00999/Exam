package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Score;

public class ScoreDao extends Dao {

    /**
     * 成績を1件取得
     */
    public Score get(String studentNo, String subject) throws Exception {

        Score score = new Score();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from score where student_no = ? and subject = ?"
            );

            statement.setString(1, studentNo);
            statement.setString(2, subject);

            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                score.setStudentNo(rSet.getString("student_no"));
                score.setSubject(rSet.getString("subject"));
                score.setScore(rSet.getInt("score"));
            } else {
                score = null;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return score;
    }

    /**
     * 成績登録
     */
    public void insert(String studentNo, String subject, int scoreValue) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "insert into score (student_no, subject, score) values (?, ?, ?)"
            );

            statement.setString(1, studentNo);
            statement.setString(2, subject);
            statement.setInt(3, scoreValue);

            statement.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
    }

    /**
     * 成績更新
     */
    public void update(String studentNo, String subject, int scoreValue) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "update score set score = ? where student_no = ? and subject = ?"
            );

            statement.setInt(1, scoreValue);
            statement.setString(2, studentNo);
            statement.setString(3, subject);

            statement.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
    }
}