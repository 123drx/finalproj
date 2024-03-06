package daniel.finalproj.Objects;

import java.util.ArrayList;

public class Hour {
    private ArrayList<Lesson> lessons;
    public Hour()
    {
        this.lessons= new ArrayList<>();
    }
   
    public Hour(Hour otherHour) {
        this.lessons = new ArrayList<>();
        for (Lesson lesson : otherHour.lessons) {
            this.lessons.add(new Lesson(lesson));
        }
    }
    

    public Hour(ArrayList<Lesson>lessons)
    {
        this.lessons = lessons;
    }
    public void addlesson(Lesson lesson)
    {
        this.lessons.add(lesson);
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    
}
