package bean;

public class Score {

    private String studentNo; // 学生番号
    private String subject;   // 科目
    private int score;        // 点数

    // 学生番号
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    // 科目
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // 点数
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}