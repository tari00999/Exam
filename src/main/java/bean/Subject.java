package bean;

public class Subject {
    private String code;
    private String name;
    private School school;

    // getter / setter
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
}