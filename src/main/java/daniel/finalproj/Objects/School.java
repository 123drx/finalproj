package daniel.finalproj.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import oshi.hardware.SoundCard;

public class School {
    String SchoolName;
    ArrayList<Teacher> Teachers = new ArrayList<>();
    ArrayList<SchoolClass> Classes = new ArrayList<>();
    Schedule schedule = new Schedule();

    public School() {

    }

    // Copy constructor
    public School(School otherSchool) {
        this.SchoolName = otherSchool.getSchoolName();

        // Copy teachers
        for (Teacher teacher : otherSchool.getTeachers()) {
            this.Teachers.add(new Teacher(teacher));
        }

        // Copy classes
        for (SchoolClass schoolClass : otherSchool.getClasses()) {
            this.Classes.add(new SchoolClass(schoolClass));
        }

        // Copy schedule
        if (otherSchool.schedule != null)
            this.schedule = new Schedule(otherSchool.schedule);
    }

    public School crossover(School parent1, School parent2) {
        School RetSchool = new School(parent1);
        Schedule Retschedule = new Schedule(RetSchool.getSchedule());
        Random random = new Random();
        int randomint = random.nextInt(2);
        for (int daY = 0; daY < Retschedule.getDays().length; daY++) {
            Day day = new Day(Retschedule.getDays()[daY]);
            if (randomint == 0) {
                int crossoverDayPoint = random.nextInt(1, Schedule.MAX_DAY - 1);
                if (crossoverDayPoint > daY) {
                    Retschedule.getDays()[daY].setHours(parent2.getSchedule().getDays()[daY].getHours());
                }

            } else {
                for (int Hour = 0; Hour < day.getHours().length; Hour++) {
                    int crossoverHourPoint = random.nextInt(2, Day.MAX_HOUR - 2);
                    if (Hour > crossoverHourPoint) {
                        Retschedule.getDays()[daY].getHours()[Hour]
                                .setLessons(parent2.getSchedule().getDays()[daY].getHours()[Hour].getLessons());
                    }
                }
            }
        }
        setSchedule(Retschedule);

        return RetSchool;
    }

    public void FillScheduleWithLessons() {

        ArrayList<List<Subject>> ClassesSubjects = new ArrayList<>();
        for (int index = 0; index < this.Classes.size(); index++) {
            ClassesSubjects.add(this.getClasses().get(index).getSubjects());
        }
        Random rand = new Random();
        int randomIndex = 0;
        Schedule Schedulee = new Schedule(schedule);
        for (int day = 0; day < Schedule.MAX_DAY; day++) {
            for (int hour = 0; hour < Day.MAX_HOUR; hour++) {
                if (hour != Day.LUNCH_HOUR - Day.STARTING_HOUR)
                    for (int index = 0; index < this.Classes.size(); index++) {
                        randomIndex = rand.nextInt(ClassesSubjects.get(index).size());
                        Lesson l = new Lesson(ClassesSubjects.get(index).get(randomIndex).getSubjectName(),
                                ClassesSubjects.get(index).get(randomIndex).getTeachersName());
                        l.setClassName(this.Classes.get(index).getClassName());
                        Schedulee.getDays()[day].getHours()[hour].addlesson(l);
                        new Lesson();
                    }

            }

        }
        this.schedule = Schedulee;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public ArrayList<ArrayList<Subject>> getallSubjects() {
        ArrayList<ArrayList<Subject>> retlist = new ArrayList<>();
        for (SchoolClass s : this.Classes) {
            retlist.add(s.getSubjects());
        }
        return retlist;
    }

    public void initializeBreakFast() {
        for (int day = 0; day < Schedule.MAX_DAY; day++) {
            for (int hour = 0; hour < Day.MAX_HOUR; hour++) {
                if (hour == Day.LUNCH_HOUR - Day.STARTING_HOUR) {
                    Lesson s = new Lesson("BreakFast", "Non", day, hour);
                    for (int i = 0; i < Classes.size(); i++) {
                        s.setClassName(Classes.get(i).getClassName());
                        schedule.getDays()[day].getHours()[hour].addlesson(s);
                    }

                }
            }
        }

    }

    public Schedule Mutate(ArrayList<ArrayList<Subject>> subjects) {
        Schedule s = new Schedule(this.getSchedule());
        int randomDay;
        int RundomSubjectIndex;
        int RandomHour;
        Random rand = new Random();
        for (int i = 0; i < Classes.size(); i++) {
            randomDay = rand.nextInt(Schedule.MAX_DAY);
            RandomHour = rand.nextInt(Day.MAX_HOUR);
            while (RandomHour == Day.LUNCH_HOUR - Day.STARTING_HOUR) {
                RandomHour = rand.nextInt(Day.MAX_HOUR);

            }
            RundomSubjectIndex = rand.nextInt(subjects.get(i).size());
            Lesson lesson = new Lesson(subjects.get(i).get(RundomSubjectIndex).getSubjectName(),
                    subjects.get(i).get(RundomSubjectIndex).getTeachersName(), Classes.get(i).getClassName());
            s.getDays()[randomDay].getHours()[RandomHour].getLessons().set(i, lesson);

        }
        this.schedule = s;
        return s;
    }
    

    public Schedule BigMutate(ArrayList<ArrayList<Subject>> subjects) {
        Schedule s = new Schedule(this.getSchedule());
        int randomDay;
        int RundomSubjectIndex;
        int RandomHour;
        Random rand = new Random();
        for (int Change = 0; Change < 3; Change++) {
            for (int i = 0; i < Classes.size(); i++) {
                randomDay = rand.nextInt(Schedule.MAX_DAY);
                RandomHour = rand.nextInt(Day.MAX_HOUR);
                while (RandomHour == Day.LUNCH_HOUR - Day.STARTING_HOUR) {
                    RandomHour = rand.nextInt(Day.MAX_HOUR);

                }
                RundomSubjectIndex = rand.nextInt(subjects.get(i).size());
                Lesson lesson = new Lesson(subjects.get(i).get(RundomSubjectIndex).getSubjectName(),
                        subjects.get(i).get(RundomSubjectIndex).getTeachersName(), Classes.get(i).getClassName());
                s.getDays()[randomDay].getHours()[RandomHour].getLessons().set(i, lesson);

            }
        }
        this.schedule = s;
        return s;
    }

    public Schedule BigMutate2(ArrayList<ArrayList<Subject>> subjects) {
        Schedule s = this.getSchedule();
        int randomDay;
        int RundomSubjectIndex;
        int RandomHour;
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
            for (int num = 0; num < Classes.size(); num++) {
                randomDay = rand.nextInt(Schedule.MAX_DAY);
                RandomHour = rand.nextInt(Day.MAX_HOUR);
                while (RandomHour == Day.LUNCH_HOUR - Day.STARTING_HOUR) {
                    RandomHour = rand.nextInt(Day.MAX_HOUR);
                }
                RundomSubjectIndex = rand.nextInt(subjects.get(num).size());
                Lesson lesson = new Lesson(subjects.get(num).get(RundomSubjectIndex).getSubjectName(),
                        subjects.get(num).get(RundomSubjectIndex).getTeachersName(),
                        this.Classes.get(num).getClassName());
                if (s.getDays()[randomDay].getHours()[RandomHour].getLessons().size() == 0)
                    s.getDays()[randomDay].getHours()[RandomHour].getLessons().set(num, lesson);
            }
        }
        this.schedule = s;
        return s;
    }

    public void printSchool() {
        System.out.println("School : " + this.SchoolName);
        System.out.println("Schedule :");
        if (this.schedule != null) {
            this.schedule.printSchedule();
        } else {
            System.out.println("Schedule Empty");
        }
        System.out.println(
                "================================================================================Teachers================================================================================");
        for (Teacher t : Teachers) {
            t.printTeacher();
        }
        System.out.println(
                "================================================================================Classes================================================================================");
        for (SchoolClass sc : getClasses()) {
            sc.printClass();
        }
    }

    public void adjustEmptySubjects() {
        for (int i = 0; i < Classes.size(); i++) {
            Classes.get(i).adjustEmptySubject();
        }
    }

    public void addLockedLesson(String ClassName, Lesson LockedLesson) {
        int index = getClassIndexByName(ClassName);
        if (index != -1)
            this.Classes.get(index).addLockedLesson(LockedLesson);
    }

    public int Elpaces() {
        int count = 0;
        HashMap<String, Integer> teacherCountMap = new HashMap<>();

        for (int day = 0; day < Schedule.MAX_DAY; day++) {
            for (int hour = 0; hour < Day.MAX_HOUR; hour++) {
                teacherCountMap = new HashMap<>();
                for (Lesson l : this.schedule.getDays()[day].getHours()[hour].getLessons()) {
                    String teacherName = l.getTeacher();
                    if (teacherName != null) {
                        teacherCountMap.put(teacherName, teacherCountMap.getOrDefault(teacherName, 0) + 1);
                    }
                }
            }
        }

        for (int teacherCount : teacherCountMap.values()) {
            if (teacherCount > 1) {
                count++;
            }
        }
        return count;
    }

    public double CountLockedBreaks(String ClassName) {
        int count = 0;
        int ClassINdex = getClassIndexByName(ClassName);
        int worstOption=getClasses().get(ClassINdex).getLockedLessons().size();
        for (Lesson lesson : getClasses().get(ClassINdex).getLockedLessons()) {
            Lesson l = this.schedule.getDays()[lesson.getDay()].getHours()[lesson.getHour()].getLessons()
                    .get(ClassINdex);
            if (l.getClassName().equals(lesson.getClassName())) {
                if (!l.getLessonSubject().equals(lesson.getLessonSubject())
                        && !l.getTeacher().equals(lesson.getTeacher())) {
                    count++;
                }
            }

        }
        if(count ==0)
        {
            return 0;
        }
        else
        {
            return ((count/worstOption)*100);
        }
    }

    
    public void printEvaluation() {
        String s = "Evaluation for School : " + SchoolName + "\n";

        double hourmismutchs = CountTeachersHourMisMutch();
        s += "Hour Mismutches : " + hourmismutchs + "\n";

        double Elapces = countElapces();
        s += "Elapces : " + Elapces + "\n";

        for (SchoolClass clas : this.Classes) {
            String className = clas.getClassName();
            s += " ================================ Class : " + className + " ================================\n";

            Double priority = EvaluatePriority(className);
            s += "Priority : " + priority + "\n";
            double MidEmptys = CountEmptyClassInMidSchrdule(className);
            s += "MidEmptys : " + MidEmptys + "\n";

            double LockedLessonsBreaks = CountLockedBreaks(className);
            s += "LockedLessonsBreaks : " + LockedLessonsBreaks + "\n";
            double Distancescore = EvalWeeklyHours(className);
            List<Integer> Distances = WeeklyDistances(className);
            s += "Distances : " +Distancescore+ "\n";
            s += "(";
            for (int distance : Distances) {
                s += distance + ",";
            }
            s += ")\n";
            s += "\n hours we have : \n";
            for (Subject subj : clas.getSubjects()) {
                int classweeklyhours = CountWeeklyHours(subj, className);
                s += subj.getSubjectName();
                s += " - " + classweeklyhours + "\t";
            }
            s += "\n hours we need : \n";
            for (Subject subj : clas.getSubjects()) {
                s += subj.getSubjectName();
                s += " - " + subj.getWeeklyHours() + "\t";
            }

        }

        System.out.println(s);

    }

    public double evaluateSchool() {
        double value = 100;

        double hourmismutchs = CountTeachersHourMisMutch();
        double priority=0;
        double MidEmptys=0;
        double Distances=0;
        double LockedLessonsBreaks=0;
        double Elapces = countElapces();
        for (SchoolClass s : this.Classes) {
            String className = s.getClassName();

            priority += EvaluatePriority(className);

            MidEmptys += CountEmptyClassInMidSchrdule(className);

            Distances += EvalWeeklyHours(className);

            LockedLessonsBreaks += CountLockedBreaks(className);
        }
        priority = priority/Classes.size();
        MidEmptys = MidEmptys/Classes.size();
        Distances =Distances/Classes.size();
        LockedLessonsBreaks = LockedLessonsBreaks/Classes.size();
        //System.out.println("=------------------------------------------------------");
        //System.out.println("priority " +priority+"MidEmptys "+MidEmptys+"Distances "+ +Distances+"LockedLessonsBreaks "+LockedLessonsBreaks+"hourmismutchs "+hourmismutchs+"Elapces " +Elapces);
        // value = 100-(hourmismutchs * 0.3 + Elapces * 0.3 + Distances * 0.2 +MidEmptys * 0.1 + priority * 0.1 +LockedLessonsBreaks * 0.1 );
        double val1 = (hourmismutchs*0.55 + Elapces*0.45);
        double val2 = ( Distances*0.5 + MidEmptys*0.2 + priority*0.15 + LockedLessonsBreaks*0.15);
        value =100- (val1*0.60 + val2*0.30);
        return value;
    }

    public School tournamentSelection(List<School> Population, List<Double> Fitnesses, List<Integer> SortedIndexes) {
        Random rand = new Random();
        // get 2 random schools
        int randomnumber1 = rand.nextInt(0, SortedIndexes.size() / 2);
        int randomnumber2 = rand.nextInt(0, SortedIndexes.size() / 2);
        // check if they are the same one and if so replace one of theme
        while (randomnumber1 == randomnumber2) {
            randomnumber2 = rand.nextInt(0, SortedIndexes.size());
        }
        // get there evaluations
        double candidate1Score = Fitnesses.get(randomnumber1);
        double candidate2Score = Fitnesses.get(randomnumber2);
        // System.out.println("Candidates = "+ randomnumber1 +","+ randomnumber2+".");
        // return the higher evaluation
        while (candidate1Score == candidate2Score) {
            randomnumber2 = rand.nextInt(0, SortedIndexes.size());
            candidate2Score = Fitnesses.get(randomnumber2);
        }
        // System.out.println("Best Score "+Evaluations.get(SortedIndexes.get(0)));
        // System.out.println("Worst Score
        // "+Evaluations.get(SortedIndexes.get(SortedIndexes.size()-1)));
        // System.out.println("Candidate : "+ randomnumber1 +" Score : " +
        // candidate1Score + "\nCandidate2 : "+randomnumber2+" Score :
        // "+candidate2Score);
        if (candidate1Score > candidate2Score) {
            setSchool(Population.get(randomnumber1));
            return Population.get(randomnumber1);
        } else {
            setSchool(Population.get(randomnumber2));
            return Population.get(randomnumber2);
        }
    }

    public void setSchool(School otherSchool) {
        this.SchoolName = otherSchool.getSchoolName();

        // Deep copy for Teachers
        for (Teacher teacher : otherSchool.Teachers) {
            this.Teachers.add(new Teacher(teacher));
        }

        // Deep copy for Classes
        for (SchoolClass schoolClass : otherSchool.Classes) {
            this.Classes.add(new SchoolClass(schoolClass));
        }
        this.schedule = otherSchool.getSchedule();
    }

    // public Double EvaluatePriority(String ClassName) {
    //     Double d = 0.0;
    //     for (int day = 0; day < Schedule.MAX_DAY; day++) {
    //         for (int hour = 0; hour < Day.MAX_HOUR; hour++) {
    //             if (hour != Day.LUNCH_HOUR - Day.STARTING_HOUR) {
    //                 for (Lesson l : this.schedule.getDays()[day].getHours()[hour].getLessons()) {
    //                     if (!l.getLessonSubject().equals("Empty")) {
    //                         Teacher currentTeacher = getTeacherByName(l.getTeacher());
    //                         if (l.getClassName() == null) {
    //                             printSchool();
    //                             System.out.println("RealPrint");
    //                             l.printlesson();
    //                             System.out.println("Day : " + day + "Hour : " + hour);
    //                         }
    //                         Subject s = getSubject(l.getClassName(), l.getLessonSubject());
    //                         ArrayList<Cords> cordinats = currentTeacher.GetAvaliability();
    //                         int priority = s.getPriority();
    //                         int classindex = getClassIndexByName(ClassName);
    //                         if (priority > 3 || priority < 3) {
    //                             for (Cords cords : cordinats) {
    //                                 if (!schedule.getDays()[cords.getDay()].getHours()[cords.getHour()].getLessons()
    //                                         .get(classindex).getTeacher().equals(l.getTeacher())) {
    //                                     if (!schedule.getDays()[cords.getDay()].getHours()[cords.getHour()].getLessons()
    //                                             .get(classindex).getLessonSubject().equals(l.GetLesson())) {
    //                                         if (priority == 1) {
    //                                             d += 1.5;
    //                                         } else if (priority == 2) {
    //                                             d += 1;
    //                                         } else if (priority == 4) {
    //                                             d += 1;
    //                                         } else if (priority == 5) {
    //                                             d += 1.5;
    //                                         }
    //                                     }
    //                                 }
    //                             }
    //                         }
    //                     }
    //                 }
    //             }
    //         }
    //     }
    //     return d;
    // }

    public Double EvaluatePriority(String ClassName) {
        Double d = 0.0;
        int ClassIndex = getClassIndexByName(ClassName);
        SchoolClass class1 = Classes.get(ClassIndex);
        for (Subject s : class1.getSubjects()) {
            int priority = s.getPriority();
            if (priority > 3 || priority < 3) {
                Teacher teacher = getTeacherByName(s.getTeachersName());
                List<Cords> cordinates = teacher.GetAvaliability();
                int WeeklyHours = s.getWeeklyHours();
                for (Cords cords : cordinates) {
                    if (WeeklyHours > 0) {
                        if (!schedule.getDays()[cords.getDay()].getHours()[cords.getHour()].getLessons().get(ClassIndex)
                                .getLessonSubject().equals(s.getSubjectName())
                                && !schedule.getDays()[cords.getDay()].getHours()[cords.getHour()].getLessons()
                                        .get(ClassIndex)
                                        .getTeacher().equals(s.getTeachersName())) {
                            if (priority == 1||priority == 5) {
                                d += 1.5;
                                WeeklyHours--;
                            }
                            if (priority == 2||priority == 4) {
                                d += 1;
                                WeeklyHours--;
                            }
                            
                        }
                    }
                }

            }
        }
        return d;
    }

    public Subject getSubject(String ClassName, String SubjectName) {
        // System.out.println(ClassName);
        SchoolClass sc = getClasses().get(this.getClassIndexByName(ClassName));
        for (Subject sub : sc.getSubjects()) {
            if (sub.getSubjectName().equals(SubjectName)) {
                return sub;
            }
        }
        return null;
    }

    public int CountWeeklyHours(Subject s, String ClassName) {
        int count = 0;
        for (int day = 0; day < Schedule.MAX_DAY; day++) {
            for (int hour = 0; hour < Day.MAX_HOUR; hour++) {
                for (Lesson l : this.schedule.getDays()[day].getHours()[hour].getLessons()) {
                    if (l.getClassName().equals(ClassName)) {
                        if (l.getLessonSubject().equals(s.getSubjectName())
                                && l.getTeacher().equals(s.getTeachersName())) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }
    public List<Integer> WeeklyDistances(String ClassName) {
        List<Integer> list = new ArrayList<>();
        for (Subject s : this.getClasses().get(getClassIndexByName(ClassName)).getSubjects()) {
            int count = CountWeeklyHours(s, ClassName);
            int ret = s.getWeeklyHours() - count;
            list.add(ret);
        }

        return list;
    }

    public double EvalWeeklyHours(String ClassName) {
        List<Integer> WeeklyDistances = WeeklyDistances(ClassName);
        double AllDistances=0;
        int AllWeeklyHours =0;
        double Eval =0;
        for (Subject s : this.getClasses().get(getClassIndexByName(ClassName)).getSubjects()) {
            AllWeeklyHours += s.getWeeklyHours();
        }
        for(int i : WeeklyDistances)
        {
            if(i>=0)
            {
                AllDistances += i;
            }
            else
            {
                AllDistances -= i;
            }
        }
        Eval = ((AllDistances/AllWeeklyHours)*100);
        return Eval;
    }

    public static ArrayList<Integer> countNonUnique(ArrayList<Lesson> lessons) {
        Map<String, Integer> lessonCountMap = new HashMap<>();
        
        for (Lesson lesson : lessons) {
            String lessonKey = "" + lesson.getTeacher();
            lessonCountMap.put(lessonKey, lessonCountMap.getOrDefault(lessonKey, 0) + 1);
        }
        
        Set<String> uniqueLessonKeys = new HashSet<>();
        ArrayList<Integer> nonUniqueCounts = new ArrayList<>();
        
        for (Lesson lesson : lessons) {
            String lessonKey = "" + lesson.getTeacher();
            if (lessonCountMap.get(lessonKey) > 1 && !uniqueLessonKeys.contains(lessonKey)) {
                nonUniqueCounts.add(lessonCountMap.get(lessonKey));
                uniqueLessonKeys.add(lessonKey);
            }
        }
        
        return nonUniqueCounts;
    }

    public int countElapcesin(int day, int hour) {
        Schedule s = getSchedule();
        int ret = 0;
        ArrayList<Lesson> list = s.getDays()[day].getHours()[hour].getLessons();
        List<Integer> values = countNonUnique(list);
        for (int value : values) {
            ret += value;
        }
        return ret;
    }

    public double countElapces() {
        double count = 0;
        int WorstScore = 0;
        for (int day = 0; day < Schedule.MAX_DAY; day++) {
            for (int hour = 0; hour < Day.MAX_HOUR; hour++) {
                if (hour != Day.LUNCH_HOUR - Day.STARTING_HOUR) {
                    count +=countElapcesin(day, hour);
                    WorstScore+=Classes.size();
                }
            }
        }
        count = ((count /WorstScore)*100);
        return count;
    }

    public double CountEmptyClassInMidSchrdule(String ClassName) {
        int index = 0;
        double Count = 0;
        double worstOption=0;
        double Eval =0;
        int ClassIndex = getClassIndexByName(ClassName);
        for (int day = 0; day < Schedule.MAX_DAY; day++) {
            for (int hour = Day.MAX_HOUR - 1; hour > 0; hour--) {
                if (hour != Day.LUNCH_HOUR - Day.STARTING_HOUR) {
                    Lesson l = this.schedule.getDays()[day].getHours()[hour].getLessons().get(ClassIndex);
                    if (l.getClassName() == ClassName) {
                        if (!l.getLessonSubject().equals("Empty")) {
                            index = hour;
                            worstOption +=(index);
                            break;
                        }
                    }
                }
            }
            for (int hour = 0; hour < index; hour++) {
                if (hour != Day.LUNCH_HOUR - Day.STARTING_HOUR) {
                    Lesson l = this.schedule.getDays()[day].getHours()[hour].getLessons().get(ClassIndex);
                    if (l.getClassName() == ClassName) {
                        if (l.getLessonSubject().equals("Empty")) {
                            Count++;
                        }
                    }
                }

            }
        }
        Eval = ((Count/worstOption)*100);
        return Count;

    }

    public double CountTeachersHourMisMutch() {
        double Count = 0;
        int worstScore =0;
        for (int i = 0; i < Schedule.MAX_DAY; i++) {
            for (int j = 0; j < Day.MAX_HOUR; j++) {
                if (j != Day.LUNCH_HOUR - Day.STARTING_HOUR) {
                    for (Lesson l : this.schedule.getDays()[i].getHours()[j].getLessons()) {
                        worstScore++;
                        if (!l.getLessonSubject().equals("Empty")) {
                            Teacher t = Teachers.get(getTeacherIndex(l.getTeacher()));
                            if (t.getHourPrefrences()[i][j] == false) {
                                Count++;
                            }
                        }
                    }
                }
            }
        }
        
        Count = ((Count/worstScore)*100);
        return Count;
    }

    public School(String Name) {
        this.SchoolName = Name;
        this.schedule = new Schedule();
        this.Teachers = new ArrayList<>();
        this.Classes = new ArrayList<>();
    }

    public void addTeacher(Teacher t) {
        if (getTeacherIndex(t.getName()) == -1) {
            this.Teachers.add(t);
        }
    }

    public int getClassIndexByName(String Name) {
        int Index = -1;
        for (int i = 0; i < this.Classes.size(); i++) {
            if (this.getClasses().get(i).getClassName().equals(Name)) {
                return i;
            }

        }
        return Index;
    }

    public void addsubject(Subject Subject, String ClassName) {
        if (getClassIndexByName(ClassName) == -1) {
            System.out.println("ClassName Is Incorrect");

        } else if (getClasses().get(getClassIndexByName(ClassName)).IsSubjectExist(Subject)) {
            System.out.println("Subject Already Exists");
        } else if (getTeacherIndex(Subject.getTeachersName()) == -1) {
            System.out.println("Teacher Dosnt Exist");
        } else {
            this.Classes.get(getClassIndexByName(ClassName)).addSubject(Subject);
            this.Teachers.get(getTeacherIndex(Subject.getTeachersName())).AdjustWeeklyHours(Subject);
        }

    }

    public int getTeacherIndex(String TeacherName) {
        int index = -1;
        for (int i = 0; i < Teachers.size(); i++) {
            if (Teachers.get(i).getName().equals(TeacherName)) {
                return i;
            }
        }
        return index;
    }

    public void addclass(SchoolClass clas) {
        if (clas != null)
            this.Classes.add(clas);
    }

    public List<Teacher> getTeachers(List<String> TeachersNames) {
        List<Teacher> list = new ArrayList<>();
        for (String Name : TeachersNames) {
            list.add(getTeacherByName(Name));
        }
        return list;
    }

    public SchoolClass GetClassByName(String Name) {
        for (SchoolClass sc : getClasses()) {
            if (sc.getClassName().equals(Name)) {
                return sc;
            }
        }
        return null;
    }

    public Teacher getTeacherByName(String Name) {
        for (Teacher t : Teachers) {
            if (t.getName().equals(Name)) {
                return t;
            }
        }
        return null;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public ArrayList<Teacher> getTeachers() {
        return Teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        Teachers = teachers;
    }

    public ArrayList<SchoolClass> getClasses() {
        return Classes;
    }

    public void setClasses(ArrayList<SchoolClass> classes) {
        Classes = classes;
    }

}
