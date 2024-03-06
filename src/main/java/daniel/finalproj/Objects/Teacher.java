package daniel.finalproj.Objects;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Teachers")
public class Teacher {
    public int MaxDays = Schedule.MAX_DAY;
    public int MaxHours = Day.MAX_HOUR;
    public int LunchHour = Day.LUNCH_HOUR;
    public int StartingHour = Day.STARTING_HOUR;

    private String name;
    private boolean[][] HourPrefrences = new boolean[MaxDays][MaxHours];
    private boolean[] DayConstrains = new boolean[MaxDays];
    private List<Subject> Subjects = new ArrayList<>();

    private static final Map<Integer, String> daysOfWeekMap = new HashMap<>();
    static {
        daysOfWeekMap.put(0, "Sunday");
        daysOfWeekMap.put(1, "Monday");
        daysOfWeekMap.put(2, "Tuesday");
        daysOfWeekMap.put(3, "Wednesday");
        daysOfWeekMap.put(4, "Thursday");
    }

    public Teacher(Teacher otherTeacher) {
        this.name = otherTeacher.name;
        // Deep copy for HourPreferences
        for (int i = 0; i < MaxDays; i++) {
            System.arraycopy(otherTeacher.getHourPrefrences()[i], 0, this.getHourPrefrences()[i], 0, MaxHours);
        }

        // Deep copy for DayConstraints
        System.arraycopy(otherTeacher.getDayConstrains(), 0, this.getDayConstrains(), 0, MaxDays);

        // Copy Subjects (assuming it's just a list of names, so a shallow copy is fine)
        this.Subjects = new ArrayList<>(otherTeacher.Subjects);
    }

    public ArrayList<Cords> GetAvaliability()
    {
        ArrayList<Cords> CordsList = new ArrayList<>();
        for (int day = 0; day < MaxDays; day++) {
            for (int hour = 0; hour < MaxHours; hour++) {
                if(HourPrefrences[day][hour])
                {
                    Cords cords = new Cords(day,hour);
                    CordsList.add(cords);
                }
            }
        }
        return CordsList;
    }
    public void printTeacher()
    {
        String s = "Name : "+name +"\n";
        for(Subject sub : Subjects)
        {
            s += ""+sub.toString()+"\t";
        }
        s+= "\n";
        System.out.println(s);
    }

    public Constrains[] getConstrains() {
        int starthour = 0;
        int EndHour = 0;
        Constrains[] consts = new Constrains[MaxDays];
        for (int day = 0; day < MaxDays; day++) {
            boolean bol = false;
            if (this.getDayConstrains()[day] == true) {
                for (int hour = 0; hour < MaxDays; hour++) {
                    if (this.getHourPrefrences()[day][hour] == true && bol == false) {
                        bol = true;
                        starthour = hour;

                    } else if (this.getHourPrefrences()[day][hour] == false) {
                        if (bol == true) {
                            EndHour = hour;
                            consts[day] = new Constrains(starthour, EndHour);
                            break;
                        }
                    }
                }
            } else {
                consts[day] = null;
            }
        }
        return consts;
    }

    public void printSubjects() {
        String s = "Subjects : ";
        for (Subject str : this.Subjects) {
            s = s + " , " + str.getSubjectName();
        }
        System.out.println(s);
    }

    public Teacher(String name, boolean[][] hourPrefrences, boolean[] dayConstrains, List<Subject> subjects) {
        this.name = name;
        HourPrefrences = hourPrefrences;
        DayConstrains = dayConstrains;
        Subjects = subjects;
    }

    public Teacher() {

    }

    public Teacher(String Name) {
        this.name = Name;
    }

    public void addsubject(Subject sbj) {
        this.Subjects.add(sbj);
    }

    public int getsubjectindex(Subject s) {
        for (int i = 0; i < Subjects.size(); i++) {
            if (s.getSubjectName().equals(Subjects.get(i).getSubjectName())
                    && s.getTeachersName().equals(Subjects.get(i).getTeachersName())) {
                return i;
            }
        }
        return -1;
    }

    public void AdjustWeeklyHours(Subject s) {
        for (Subject subj : Subjects) {
            if (subj.getSubjectName().equals(s.getSubjectName()) && subj.getTeachersName().equals(s.getTeachersName())
                    && subj.getWeeklyHours() != s.getWeeklyHours()) {
                this.Subjects.get(getsubjectindex(subj)).setWeeklyHours(s.getWeeklyHours());
            }
        }
    }

    public List<Subject> getSubjects() {
        return Subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.Subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }



    public int getWeeklyHours() {
        int count = 0;
        for (int day = 0; day < MaxDays; day++) {
            for (int Hour = 0; Hour < MaxHours; Hour++) {
                if (!(Hour == LunchHour - StartingHour)) {
                    if (this.getHourPrefrences()[day][Hour] == true) {
                        count++;
                    }
                }
            }
        }
        return count;
    }


    public void setConstrains(Constrains[] constrains) {
        for (int i = 0; i < MaxDays; i++) {
            if (constrains[i] == null) {
                this.DayConstrains[i] = false;
                for (int j = 0; j < MaxDays; j++) {
                    HourPrefrences[i][j] = false;
                }
            } else {
                this.DayConstrains[i] = true;
                for (int j = constrains[i].getStartHour() - StartingHour; j <= constrains[i].getEndHour()
                        - StartingHour; j++) {
                    HourPrefrences[i][j] = true;
                }
            }
        }
    }

    public void setConstrains1(Constrains[] constrains) {
        for (int i = 0; i < MaxDays; i++) {
            if (constrains[i] == null) {
                this.DayConstrains[i] = false;
                for (int j = 0; j < MaxDays; j++) {
                    HourPrefrences[i][j] = false;
                }
            } else {
                this.DayConstrains[i] = true;
                for (int j = constrains[i].getStartHour(); j <= constrains[i].getEndHour(); j++) {
                    HourPrefrences[i][j] = true;
                }
            }
        }
    }

    public int getdistancefromhour(int day) {
        int retint = 0;
        for (int hour = 0; hour < this.MaxHours; hour++) {
            if (this.getHourPrefrences()[day][hour]) {

            }
        }
        return retint;
    }

    public void setConstrains(boolean[][] constrains) {
        this.HourPrefrences = constrains;

    }

    public void SetDayConstrains(boolean[] arr) {
        this.DayConstrains = arr;
    }

    public boolean[][] getHourPrefrences() {
        return HourPrefrences;
    }

    public boolean[] getDayConstrains() {
        return DayConstrains;
    }

    public void printconstrains() {
        System.out.println("Days he can work");
        for (int i = 0; i < MaxDays; i++) {
            if (this.DayConstrains[i] == true) {
                System.out.println("can Work on " + daysOfWeekMap.get(i) + " ");
                System.out.print("in ");
                for (int j = 0; j < MaxHours; j++) {
                    if (this.HourPrefrences[i][j] == true) {
                        System.out.print(" " + (j + StartingHour) + ",");
                    }
                }
                System.out.println();
            } else {
                System.out.println("Can't Work On " + daysOfWeekMap.get(i));
            }
        }
    }

    public void printDayConstrains() {
        for (int i = 0; i < this.DayConstrains.length; i++) {
            if (this.DayConstrains[i] == true) {
                System.out.println("Day " + (i + 1) + " = " + "True");
            } else {
                System.out.println("Day " + (i + 1) + " = " + "False");
            }

        }
    }

    public List<Lesson> GetLessonFromTeachers(List<Teacher> teachers) {
        List<Lesson> Subjects = new ArrayList<>();
        Set<Lesson> TempSubject = new HashSet<>();
        for (Teacher t : teachers) {
            for (Subject subject : t.getSubjects()) {
                Lesson lesson = new Lesson(subject.getSubjectName(), t.getName());
                TempSubject.add(lesson);
            }
        }
        for (Lesson s : TempSubject) {
            // s.PrintLesson();
            Subjects.add(s);
        }

        return Subjects;
    }

}
