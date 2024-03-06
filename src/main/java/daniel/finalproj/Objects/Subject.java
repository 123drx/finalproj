package daniel.finalproj.Objects;

public class Subject {
    private String SubjectName;
    private String TeachersName;
    private int WeeklyHours;
    private int priority;
    public Subject()
    {}
    public Subject(String SubjectName)
    {
        this.SubjectName = SubjectName;
        this.priority = 3;
    }
    public Subject(String SubjectName,String teachesName,int WeeklyHours)
    {
        this.SubjectName= SubjectName;
        this.TeachersName=teachesName;
        this.WeeklyHours=WeeklyHours;
        this.priority = 3;

    }
    public Subject(String SubjectName,String teachesName)
    {
        this.SubjectName= SubjectName;
        this.TeachersName=teachesName;
        this.priority = 3;

    }
    public Subject(String SubjectName,String teachesName,int WeeklyHours,int proirity)
    {
        this.SubjectName= SubjectName;
        this.TeachersName=teachesName;
        this.WeeklyHours=WeeklyHours;
        this.priority = proirity;
    }
    
    public int getPriority() {
        return priority;
    }
    public void printsubject()
    {
        System.out.println("{SN: " + SubjectName + " TN: "+TeachersName+" WH: "+WeeklyHours+" P: " + priority+"}");
    }
    public String toString()
    {
        String s = "{SN: " + SubjectName + " TN: "+TeachersName+" WH: "+WeeklyHours+" P: " + priority+"}";
        return s;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public Subject(Subject otherSubject) {
        this.SubjectName = otherSubject.getSubjectName();
        this.TeachersName = otherSubject.getTeachersName();
        this.WeeklyHours = otherSubject.getWeeklyHours();
        this.priority = otherSubject.getPriority();

    }
    public String getSubjectName() {
        return SubjectName;
    }
    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }
    public String getTeachersName() {
        return TeachersName;
    }
    public void setTeachersName(String teachersName) {
        TeachersName = teachersName;
    }
    public int getWeeklyHours() {
        return WeeklyHours;
    }
    public void setWeeklyHours(int weeklyHours) {
        WeeklyHours = weeklyHours;
    }
    
    
}
