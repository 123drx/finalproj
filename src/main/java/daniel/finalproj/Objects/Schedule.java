package daniel.finalproj.Objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Schedule {
    public static final int MAX_DAY = 5;

    private Day[] days;

    private static final Map<Integer, String> daysOfWeekMap = new HashMap<>();
    static {
        daysOfWeekMap.put(0, "Sunday");
        daysOfWeekMap.put(1, "Monday");
        daysOfWeekMap.put(2, "Tuesday");
        daysOfWeekMap.put(3, "Wednesday");
        daysOfWeekMap.put(4, "Thursday");
        daysOfWeekMap.put(5, "Friday");
        daysOfWeekMap.put(6, "Saturday");
    }

    public Schedule(Schedule otherSchedule) {
        this.days = new Day[MAX_DAY];
        for (int i = 0; i < MAX_DAY; i++) {
            if (otherSchedule.days[i] != null) {
                this.days[i] = new Day(otherSchedule.days[i]);
            }
        }
    }

    public Schedule() {
        this.days = new Day[MAX_DAY];
        initializeDays();
    }

    public int CountElapces(Teacher teacher, int Day, int Hour) {
        int count = 0;
        for (Lesson lesson : getDays()[Day].getHours()[Hour].getLessons()) {
            if (lesson.getTeacher().equals(teacher.getName())) {
                count++;
            }
        }
        return count;
    }

    public void printSchedule() {
        // Define column widths
        int dayWidth = 80; // Adjust as needed
        int subjectWidth = 20; // Adjust as needed
        int teacherWidth = 20; // Adjust as needed
        int classWidth = 20; // Adjust as needed

        // Print the header row with days
        System.out.print(String.format("%-" + dayWidth + "s", "Day"));
        for (Day day : days) {
            System.out.print(String.format("%-" + dayWidth + "s", day.getName()));
        }
        System.out.println(); // Move to the next line

        // Print schedule grid
        for (int i = 0; i < Day.MAX_HOUR; i++) {
            // Print hour on the left
            System.out.print(String.format("%-" + subjectWidth + "s", (i + Day.STARTING_HOUR) + ":00"));

            // Print lessons for each day
            for (Day day : days) {
                Hour hour = day.getHours()[i];
                boolean lessonPrinted = false; // To check if lesson is printed for current hour
                for (Lesson lesson : hour.getLessons()) {
                    // Print lesson subject, teacher, and class name with padding
                    System.out.print(
                            String.format("%-" + subjectWidth + "s", lesson.getLessonSubject()) +
                                    String.format("%-" + teacherWidth + "s", lesson.getTeacher()) +
                                    String.format("%-" + classWidth + "s", lesson.getClassName()));
                    lessonPrinted = true;
                }
                if (!lessonPrinted) {
                    // If no lesson for this hour, print empty slots with padding
                    System.out.print(
                            String.format("%-" + subjectWidth + "s", "-") +
                                    String.format("%-" + teacherWidth + "s", "-") +
                                    String.format("%-" + classWidth + "s", "-"));
                }
            }
            System.out.println(); // Move to the next line
        }
    }

    public String[][] getScheduleStrings() {
        String[][] Schedule = new String[MAX_DAY][Day.MAX_HOUR + 1];

        for (int day = 0; day < MAX_DAY; day++) {
            for (int hour = 0; hour < Day.MAX_HOUR; hour++) {
                if (hour == 0) {
                    Schedule[day][0] = daysOfWeekMap.get(day);
                } else {
                    for (int lsn = 0; lsn < days[0].getHours()[0].getLessons().size(); lsn++) {
                        Schedule[day][hour] += days[day].getHours()[hour].getLessons().get(lsn).getLessonSubject()
                                + " , " + days[day].getHours()[hour].getLessons().get(lsn).getTeacher() + " | ";
                    }
                }
            }
        }

        return Schedule;
    }

    public String getScheduleAsString() {
        StringBuilder scheduleString = new StringBuilder();
        for (Day day : days) {
            scheduleString.append("Day: ").append(day.getName()).append("\n");
            for (int i = 0; i < Day.MAX_HOUR; i++) {
                Hour hour = day.getHours()[i];
                scheduleString.append("Hour ").append(i + Day.STARTING_HOUR).append(":\n");
                for (Lesson lesson : hour.getLessons()) {
                    scheduleString.append("Lesson Subject: ").append(lesson.getLessonSubject())
                            .append(", Teacher: ").append(lesson.getTeacher()).append("\n");
                }
            }
        }
        return scheduleString.toString();
    }

    public int CountWeeklyHours(Subject s) {
        int count = 0;
        for (int day = 0; day < MAX_DAY; day++) {
            for (int hour = 0; hour < Day.MAX_HOUR; hour++) {
                for (int lesson = 0; lesson < this.getDays()[day].getHours()[hour].getLessons().size(); lesson++) {
                    if (this.getDays()[day].getHours()[hour].getLessons().get(lesson).getLessonSubject()
                            .equals(s.getSubjectName())
                            && this.getDays()[day].getHours()[hour].getLessons().get(lesson).getTeacher()
                                    .equals(s.getTeachersName())) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public Schedule CrossOver(Schedule parent1, Schedule parent2) {
        int randomday = 0;
        int RandomCut = 0;
        Schedule ReturnSchedule = new Schedule(parent1);
        Random rand = new Random();
        randomday = rand.nextInt(1, MAX_DAY - 1);
        RandomCut = rand.nextInt(2);
        if (RandomCut == 1) {
            for (int day = 0; day < MAX_DAY; day++) {
                if (day >= randomday) {
                    ReturnSchedule.getDays()[day] = parent1.getDays()[day];
                } else {
                    ReturnSchedule.getDays()[day] = parent2.getDays()[day];
                }
            }
        } else {
            int randomhour = 0;
            randomhour = rand.nextInt(Day.MAX_HOUR - 2);
            for (int day = 0; day < MAX_DAY; day++) {
                for (int hour = 0; hour < Day.MAX_HOUR; hour++) {
                    if (day >= randomhour) {
                        ReturnSchedule.getDays()[day] = parent1.getDays()[day];
                    } else {
                        ReturnSchedule.getDays()[day] = parent2.getDays()[day];
                    }
                }
            }

        }

        return ReturnSchedule;
    }

    private void initializeDays() {
        for (int i = 0; i < MAX_DAY; i++) {
            days[i] = new Day(daysOfWeekMap.get(i));
        }
    }

    public Day[] getDays() {
        return days;
    }

    public void setDays(Day[] days) {
        this.days = days;
    }

    // Optionally, you can override toString() to provide a meaningful string
    // representation
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Schedule:\n");
        for (int i = 0; i < MAX_DAY; i++) {
            stringBuilder.append("Day ").append(i + 1).append(": ").append(daysOfWeekMap.get(i))
                    .append(days[i].toString()).append("\n");

        }
        return stringBuilder.toString();
    }
}
