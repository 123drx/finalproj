package daniel.finalproj.Objects;

public class Lesson {

    private String LessonSubject;
    private String Teacher;
    private String ClassName;
    private int hour;
    private int day;

    public String getClassName() {
        return ClassName;
    }
    public Lesson(String Subject,String Teacher,String ClassName,int hour ,int day)
    {
        this.LessonSubject = Subject;
        this.Teacher = Teacher;
        this.ClassName = ClassName;
        this.hour = hour;
        this.day = day;
    }
    public Lesson(Lesson otherLesson) {
        this.LessonSubject = otherLesson.LessonSubject;
        this.Teacher = otherLesson.Teacher;
        this.ClassName = otherLesson.ClassName;
        this.hour = otherLesson.hour;
        this.day = otherLesson.day;
    }
    public void Setlesson(Lesson otherLesson) {
        this.LessonSubject = otherLesson.LessonSubject;
        this.Teacher = otherLesson.Teacher;
        this.ClassName = otherLesson.ClassName;
        this.hour = otherLesson.hour;
        this.day = otherLesson.day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public Lesson(String LessonSubject, String TeachersName) {
        this.LessonSubject = LessonSubject;
        this.Teacher = TeachersName;
    }
    public Lesson(String LessonSubject, String TeachersName,int day,int hour) {
        this.LessonSubject = LessonSubject;
        this.Teacher = TeachersName;
        this.day = day;
        this.hour=hour;
    }

    public Lesson(String LessonSubject, String TeachersName, String ClassName) {
        this.LessonSubject = LessonSubject;
        this.Teacher = TeachersName;
        this.ClassName = ClassName;
    }


    public void printlesson() {
        System.out.println("{LesonSubject: : " + LessonSubject + " Teacher : " + Teacher + " ClassName : " + ClassName);
    }

    public void printLockedLesson()
    {
        System.out.println("{LesonSubject: : " + LessonSubject + " Teacher : " + Teacher + " ClassName : " + ClassName +" day : "+day+" Hour : "+hour);

    }

    public Lesson() {

    }

    public String getLessonSubject() {
        return LessonSubject;
    }

    public void setLessonSubject(String lessonSubject) {
        LessonSubject = lessonSubject;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public String GetLesson() {
        String s;
        s = "" + LessonSubject + " " + Teacher + " " + ClassName;
        return s;
    }

    public void PrintLesson() {
        String s;
        s = "" + LessonSubject + " " + Teacher + " " + ClassName;
        System.out.println(s);
    }

}
