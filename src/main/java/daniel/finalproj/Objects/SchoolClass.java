package daniel.finalproj.Objects;


import java.util.ArrayList;
import java.util.List;

import javassist.bytecode.stackmap.TypeData.ClassName;

public class SchoolClass {
    private String className;
    private ArrayList<Subject> subjects = new ArrayList<>();
    private ArrayList<String> Teachers = new ArrayList<>();
    private ArrayList<Lesson> LockedLessons = new ArrayList<>();


    public void adjustEmptySubject() {
        int MaxD = Schedule.MAX_DAY;
        int MaxH = Day.MAX_HOUR;
        int EmptySubjectWeeklyHours = (MaxD * MaxH)-MaxD;
        //EmptySubjectWeeklyHours -= MaxD;//Remove the amount of breaks in the schedule(every day 1 break)

        for (Subject subject : subjects) {
            EmptySubjectWeeklyHours -= subject.getWeeklyHours();
        }

        if (!isSubjectExist("Empty")) {
            Subject s = new Subject("Empty");
            s.setTeachersName("Non");
            s.setWeeklyHours(EmptySubjectWeeklyHours);
            System.out.println("Empty Hours : "+ EmptySubjectWeeklyHours);
            addSubject(s);
        }
        else {
            subjects.get(getSubjectIndex("Empty")).setWeeklyHours(EmptySubjectWeeklyHours);
            System.out.println(subjects.get(getSubjectIndex("Empty")).getWeeklyHours() + "<- Empty Weekly Hours");
        }
        
        
        //System.out.println("Empty Hours : "+ EmptySubjectWeeklyHours);
    }
    public void printClass()
    {
        System.out.println("Class Name : "+ this.getClassName());
        {
            System.out.println("========================================Subjects : ========================================");
            for(Subject subj : subjects)
            {
                subj.printsubject();
            }
            System.out.println("========================================Teachers : ========================================");
            for(String teacher: Teachers)
            {
                System.out.println(teacher);
            }
            System.out.println("========================================Locked Lessons========================================");
            for(Lesson l : LockedLessons)
            {
                l.printLockedLesson();
            }
        }
    }

   public Subject getSubject(String SubjectName)
   {
    for(Subject subject : subjects)
    {
        if(subject.getSubjectName().equals(SubjectName))
        return subject;
    }
    return null;
   }

    public void printSubjectsweeklyhours()
    {
        String s = "";
        for(Subject subj : subjects) 
        {
            s += subj.getSubjectName() + " : " + "Hours : "+ subj.getWeeklyHours() +"\t"; 
        }       
        System.out.println(s);
    }

    public int getSubjectIndex(String SubjectName) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getSubjectName().equals(SubjectName)) {
                return i;
            }
        }
        return -1;
    }

    public boolean IsSubjectExist(Subject SubjectName) {
        for (Subject subject : subjects) {
            if (SubjectName.getSubjectName().equals(subject.getSubjectName())&&SubjectName.getTeachersName().equals(subject.getTeachersName())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Subject> getTeachersSubjectsInClass(String TeachersName)
    {
        ArrayList<Subject> subjs = new ArrayList<>();
        for(Subject s : this.getSubjects())
        {
            if(s.getTeachersName().equals(TeachersName))
            {
                subjs.add(s);
            }
        }
        return subjs;
    }


    public void addLockedLesson(Lesson lesson) {
        lesson.setClassName(className);
        this.LockedLessons.add(lesson);
    }

    public SchoolClass() {

    }

    public SchoolClass(SchoolClass otherClass) {
        this.className = otherClass.className;
        this.LockedLessons = otherClass.LockedLessons;

        // Deep copy for subjects
        for (Subject subject : otherClass.subjects) {
            this.subjects.add(new Subject(subject));
        }

        // Copy Teachers (assuming it's just a list of names, so a shallow copy is fine)
        this.Teachers = new ArrayList<>(otherClass.Teachers);
    }

    public void printsubjects() {
        for (Subject s : this.subjects) {
            System.out.println("stubject " + s.getSubjectName());
        }
    }

    public boolean isSubjectExist(String SubjectName) {
        for (Subject s : subjects) {
            if (s.getSubjectName().equals(SubjectName)) {
                return true;
            }
        }
        return false;
    }


    public SchoolClass(String Name) {
        this.className = Name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void addteacher(String teacher) {
        this.Teachers.add(teacher);
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Subject Subject) {
        this.subjects.add(Subject);
        if (Subject.getTeachersName() != null) {
            if (!IsTeacherExists(Subject.getTeachersName())) {
                addteacher(Subject.getTeachersName());
            }
        }
    }

    public void RemoveSubject(int index) {
        this.subjects.remove(index);
    }

    public String tostring() {
        String s;
        s = "class" + className + "\n";
        s += "Subjects : ";
        for (Subject sbj : subjects) {
            s += sbj.getSubjectName() + " , ";
        }
        return s;
    }

    public boolean IsTeacherExists(String Name) {
        if (this.Teachers == null) {
            return false;
        }
        for (String t : this.Teachers) {
            if (t.equals(Name))
                return true;
        }
        return false;
    }

    public List<String> getTeachers() {
        return Teachers;
    }

    public void setTeachers(ArrayList<String> teachers) {
        Teachers = teachers;
    }

    

    public List<Lesson> getLockedLessons() {
        return LockedLessons;
    }

    public void setLockedLessons(ArrayList<Lesson> lockedLessons) {
        LockedLessons = lockedLessons;
    }

}
