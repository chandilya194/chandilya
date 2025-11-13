import java.util.Date;

public class Student {
    private String studentName;
    private String achievement;
    private String description;
    private Date date;

    public Student() {
        this.date = new Date();
    }

    public Student(String studentName, String achievement, String description) {
        this.studentName = studentName;
        this.achievement = achievement;
        this.description = description;
        this.date = new Date();
    }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getAchievement() { return achievement; }
    public void setAchievement(String achievement) { this.achievement = achievement; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}