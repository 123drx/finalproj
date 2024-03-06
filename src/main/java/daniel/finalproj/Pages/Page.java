package daniel.finalproj.Pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import daniel.finalproj.Algorithms.GeneticAlgorithm;
import daniel.finalproj.Objects.Constrains;
import daniel.finalproj.Objects.Lesson;
import daniel.finalproj.Objects.Principle;
import daniel.finalproj.Objects.Schedule;
import daniel.finalproj.Objects.School;
import daniel.finalproj.Objects.SchoolClass;
import daniel.finalproj.Objects.Subject;
import daniel.finalproj.Objects.Teacher;
import daniel.finalproj.Objects.User;
import daniel.finalproj.Repositorys.PrincipleRepository;
import daniel.finalproj.Servieses.PrincipleServies;
import daniel.finalproj.Servieses.UserServies;

@Route("/page")
@PageTitle("TestPage")
public class Page extends VerticalLayout {
    PrincipleServies ps;
    UserServies Us ;
    public Page(PrincipleServies prncplesrvs,UserServies Us) {
        this.ps = prncplesrvs;
        this.Us = Us;
        Button btn = new Button("print CrossoOver");
        btn.addClickListener(event -> printCrossover());
        Button btn1 = new Button("print Full schedule");
        btn1.addClickListener(event -> printfullSchool());
        Button btn2 = new Button("print Big Muutate");
        btn2.addClickListener(event -> printBigmutate());
        Button btn3 = new Button("print Genetic");
        btn3.addClickListener(event -> PreformGeneticAlgorithm());
        Button btn4 = new Button("print Tournament Selection");
        btn4.addClickListener(event -> printTournamentSelection());
        Button btn5 = new Button("print SortedIndexes");
        btn5.addClickListener(event -> printSortedIndexes());
        Button btn6 = new Button("print Eval1");
        btn6.addClickListener(event -> insertPrinciple());
        add(btn, btn1, btn2, btn3, btn4, btn5,btn6);
    }

    public void printTournamentSelection() {
        GeneticAlgorithm GA = new GeneticAlgorithm();
        School school = CreateSchool();
        List<School> population = GA.initializepopulation(school);
        ArrayList<Double> Fitnesses = new ArrayList<>();
        for (School scl : population) {
            Fitnesses.add(scl.evaluateSchool());
        }
        List<Integer> SortedIndexes = new ArrayList<>();
        SortedIndexes = GA.getSortedIndexes(Fitnesses);
        school.tournamentSelection(population, Fitnesses, SortedIndexes);

    }

    public void printEval1() {

        School s = CreateSchool();
        s.FillScheduleWithLessons();
        System.out.println("============================================================="+s.evaluateSchool());
        s.printEvaluation();
        s.printSchool();
    }

    public void printSortedIndexes() {
        GeneticAlgorithm GA = new GeneticAlgorithm();
        School school = CreateSchool();
        List<School> population = GA.initializepopulation(school);
        ArrayList<Double> Fitnesses = new ArrayList<>();
        for (School scl : population) {
            Fitnesses.add(scl.evaluateSchool());
        }
        List<Integer> SortedIndexes = new ArrayList<>();
        SortedIndexes = GA.getSortedIndexes(Fitnesses);
        System.out.println("Populations Fitnesses : \n");
        int count = 0;
        for (Double Fitnnes : Fitnesses) {
            count++;
            System.out.println(count + ": Fitnees" + Fitnnes + "\n");
        }
        count = 0;
        System.out.println("Sorted indexes : \n");
        for (int Fitnnes : SortedIndexes) {
            count++;
            System.out.println(": indexOF " + count + "'s score : " + Fitnnes + "\n");
        }
        System.out.println("Sorted Values : \n");
        for (int i = 0; i < SortedIndexes.size(); i++) {
            System.out.println(Fitnesses.get(SortedIndexes.get(i)));
        }
    }

    public void printCrossover() {
        School s = CreateSchool();
        s.FillScheduleWithLessons();
        School s2 = CreateSchool();
        s2.FillScheduleWithLessons();
        System.out.println(
                "======================================================s1======================================================");
        s.printSchool();
        System.out.println(
                "======================================================s2======================================================");
        s2.printSchool();
        s.crossover(s, s2);
        System.out.println(
                "======================================================After======================================================");
        s.printSchool();
    }

    public void insertPrinciple()
    {
        Principle principle = new Principle();
        principle.setName("daniel grass");
        School s = CreateSchool();
        s.adjustEmptySubjects();
        principle.setSchool(s);
        ps.InsertPrincple(principle);
        User user = new User();
        user.setRealName(principle.getName());
        user.setUsername("123drx");
        user.setPassword("danieking123");
        user.setProffesion("Principle");
        Us.insertUser(user);
        Notification.show("Done");
    }

    public void PreformGeneticAlgorithm() {
        GeneticAlgorithm algo1 = new GeneticAlgorithm();
        School s = CreateSchool();
        s.adjustEmptySubjects();
        s = algo1.Geneticalgorithm(s);
        // s.printSchool();
        // s.printEvaluation();
    }

    public void printEmptySchool() {
        School s = CreateSchool();
        s.printSchool();
    }

    public void printfullSchool() {
        School s = CreateSchool();
        s.FillScheduleWithLessons();
        s.printSchool();
    }

    public void printBigmutate() {
        School s = CreateSchool();
        s.FillScheduleWithLessons();
        s.printSchool();
        s.BigMutate(s.getallSubjects());
        System.out.println(
                "======================================================after=============================================");
        s.printSchool();

    }

    public School CreateSchool2() {
        School Created = new School("New School");
        SchoolClass class1 = new SchoolClass("יג");
        SchoolClass class2 = new SchoolClass("יד");
        Created.addclass(class1);
        Created.addclass(class2);

        Teacher Guy = new Teacher("Guy");
        Constrains[] GuysConsts = new Constrains[Schedule.MAX_DAY];
        GuysConsts[0] = new Constrains(8, 14);
        GuysConsts[4] = new Constrains(12, 16);
        Guy.setConstrains(GuysConsts);
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("1machine learning", Guy.getName()));
        subjects.add(new Subject("1python", Guy.getName()));
        subjects.add(new Subject("1operating systems", Guy.getName()));
        Guy.setSubjects(subjects);
        Created.addTeacher(Guy);

        return Created;
    }

    public School CreateSchool() {
        School Created = new School("New School");
        SchoolClass class1 = new SchoolClass("יג");
        SchoolClass class2 = new SchoolClass("יד");
        Created.addclass(class1);
        Created.addclass(class2);

        Teacher Guy = new Teacher("Guy");
        Constrains[] GuysConsts = new Constrains[Schedule.MAX_DAY];
        GuysConsts[0] = new Constrains(8, 14);
        GuysConsts[4] = new Constrains(12, 16);
        Guy.setConstrains(GuysConsts);
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("machine learning", Guy.getName()));
        subjects.add(new Subject("python", Guy.getName()));
        subjects.add(new Subject("operating systems", Guy.getName()));
        Guy.setSubjects(subjects);
        Created.addTeacher(Guy);

        Teacher kodesh = new Teacher("Harab le Kodesh");
        Constrains[] KodeshConsts = new Constrains[Schedule.MAX_DAY];
        KodeshConsts[3] = new Constrains(12, 14);
        kodesh.setConstrains(KodeshConsts);
        List<Subject> Kodeshsubjects = new ArrayList<>();
        Kodeshsubjects.add(new Subject("Kodesh", kodesh.getName()));
        kodesh.setSubjects(Kodeshsubjects);
        Created.addTeacher(kodesh);

        Teacher ilan = new Teacher("ilan");
        Constrains[] ilanConsts = new Constrains[Schedule.MAX_DAY];
        ilanConsts[0] = new Constrains(8, 16);
        ilanConsts[3] = new Constrains(8, 8);
        ilanConsts[1] = new Constrains(8, 14);
        ilanConsts[4] = new Constrains(8, 10);
        ilan.setConstrains(ilanConsts);
        List<Subject> ilansubjects = new ArrayList<>();
        ilansubjects.add(new Subject("java", ilan.getName()));
        ilansubjects.add(new Subject("project", ilan.getName()));
        ilansubjects.add(new Subject("Asmbly", ilan.getName()));
        ilan.setSubjects(ilansubjects);
        Created.addTeacher(ilan);

        Teacher trabelsy = new Teacher("trabelsy");
        Constrains[] trabelsyConsts = new Constrains[Schedule.MAX_DAY];
        trabelsyConsts[1] = new Constrains(8, 16);
        trabelsyConsts[2] = new Constrains(14, 16);
        trabelsyConsts[3] = new Constrains(14, 16);
        trabelsyConsts[4] = new Constrains(8, 14);
        trabelsy.setConstrains(trabelsyConsts);
        List<Subject> trabelsysubjects = new ArrayList<>();
        trabelsysubjects.add(new Subject("statistics", trabelsy.getName()));
        trabelsysubjects.add(new Subject("linear", trabelsy.getName()));
        trabelsy.setSubjects(trabelsysubjects);
        Created.addTeacher(trabelsy);

        Teacher mergi = new Teacher("Mergi");
        Constrains[] mergiConsts = new Constrains[Schedule.MAX_DAY];
        mergiConsts[0] = new Constrains(15, 16);
        mergiConsts[2] = new Constrains(10, 13);
        mergiConsts[3] = new Constrains(8, 9);
        mergi.setConstrains(mergiConsts);
        List<Subject> mergisubjects = new ArrayList<>();
        mergisubjects.add(new Subject("nets", mergi.getName()));
        mergi.setSubjects(mergisubjects);
        Created.addTeacher(mergi);

        Teacher Yaniv = new Teacher("Yaniv");
        Constrains[] YanivConsts = new Constrains[Schedule.MAX_DAY];
        YanivConsts[2] = new Constrains(8, 16);
        YanivConsts[3] = new Constrains(8, 13);
        Yaniv.setConstrains(YanivConsts);
        List<Subject> Yanivsubjects = new ArrayList<>();
        Yanivsubjects.add(new Subject("Python", Yaniv.getName()));
        Yanivsubjects.add(new Subject("hystory", Yaniv.getName()));
        Yanivsubjects.add(new Subject("halaha", Yaniv.getName()));
        Yaniv.setSubjects(Yanivsubjects);
        Created.addTeacher(Yaniv);

        Teacher Amsalem = new Teacher("Amsalem");
        Constrains[] AmsalemConsts = new Constrains[Schedule.MAX_DAY];
        AmsalemConsts[1] = new Constrains(15, 16);
        Amsalem.setConstrains(AmsalemConsts);
        List<Subject> Amsalemsubjects = new ArrayList<>();
        Amsalemsubjects.add(new Subject("computers", Amsalem.getName()));
        Amsalem.setSubjects(Amsalemsubjects);
        Created.addTeacher(Amsalem);

        Teacher Daniel = new Teacher("Daniel");
        Constrains[] DanielConsts = new Constrains[Schedule.MAX_DAY];
        DanielConsts[2] = new Constrains(8, 10);
        DanielConsts[3] = new Constrains(10, 16);
        DanielConsts[4] = new Constrains(15, 16);
        Daniel.setConstrains(DanielConsts);
        List<Subject> Danielsubjects = new ArrayList<>();
        Danielsubjects.add(new Subject("Life", Daniel.getName()));
        Daniel.setSubjects(Danielsubjects);
        Created.addTeacher(Daniel);

        Subject c0s0 = new Subject(Guy.getSubjects().get(0).getSubjectName(), Guy.getName(), 2);
        Subject c0s1 = new Subject(Guy.getSubjects().get(1).getSubjectName(), Guy.getName(), 3);
        // Subject c0s2 = new Subject(Guy.getSubjects().get(2).getSubjectName(),
        // Guy.getName(), 3);
        Subject c0s3 = new Subject(ilan.getSubjects().get(0).getSubjectName(), ilan.getName(), 4);
        Subject c0s4 = new Subject(ilan.getSubjects().get(1).getSubjectName(), ilan.getName(), 6, 5);
        Subject c0s5 = new Subject(trabelsy.getSubjects().get(0).getSubjectName(), trabelsy.getName(), 6);
        Subject c0s6 = new Subject(trabelsy.getSubjects().get(1).getSubjectName(), trabelsy.getName(), 3);
        Subject c0s7 = new Subject(mergi.getSubjects().get(0).getSubjectName(), mergi.getName(), 2);
        Subject c0s8 = new Subject(Daniel.getSubjects().get(0).getSubjectName(), Daniel.getName(), 2);
        Subject c0s9 = new Subject(Yaniv.getSubjects().get(1).getSubjectName(), Yaniv.getName(), 4);
        Subject c0s10 = new Subject(kodesh.getSubjects().get(0).getSubjectName(), kodesh.getName(), 2);

        Subject c1s0 = new Subject(ilan.getSubjects().get(2).getSubjectName(), ilan.getName(), 3);
        Subject c1s1 = new Subject(Guy.getSubjects().get(2).getSubjectName(), Guy.getName(), 2);
        Subject c1s2 = new Subject(Guy.getSubjects().get(0).getSubjectName(), Guy.getName(), 4);
        Subject c1s3 = new Subject(ilan.getSubjects().get(0).getSubjectName(), ilan.getName(), 5);
        Subject c1s4 = new Subject(trabelsy.getSubjects().get(1).getSubjectName(), trabelsy.getName(), 9);
        Subject c1s5 = new Subject(Amsalem.getSubjects().get(0).getSubjectName(), Amsalem.getName(), 2);
        Subject c1s6 = new Subject(Daniel.getSubjects().get(0).getSubjectName(), Daniel.getName(), 6);
        Subject c1s7 = new Subject(Yaniv.getSubjects().get(0).getSubjectName(), Yaniv.getName(), 4);
        Subject c1s8 = new Subject(Yaniv.getSubjects().get(2).getSubjectName(), Yaniv.getName(), 4);

        Created.addsubject(c0s0, class1.getClassName());
        Created.addsubject(c0s1, class1.getClassName());
        // Created.addsubject(c0s2, class1.getClassName());
        Created.addsubject(c0s3, class1.getClassName());
        Created.addsubject(c0s4, class1.getClassName());
        Created.addsubject(c0s5, class1.getClassName());
        Created.addsubject(c0s6, class1.getClassName());
        Created.addsubject(c0s7, class1.getClassName());
        Created.addsubject(c0s8, class1.getClassName());
        Created.addsubject(c0s9, class1.getClassName());
        Created.addsubject(c0s10, class1.getClassName());

        Created.addsubject(c1s0, class2.getClassName());
        Created.addsubject(c1s1, class2.getClassName());
        Created.addsubject(c1s2, class2.getClassName());
        Created.addsubject(c1s3, class2.getClassName());
        Created.addsubject(c1s4, class2.getClassName());
        Created.addsubject(c1s5, class2.getClassName());
        Created.addsubject(c1s6, class2.getClassName());
        Created.addsubject(c1s7, class2.getClassName());
        Created.addsubject(c1s8, class2.getClassName());

        Lesson l = new Lesson();
        l.setTeacher(ilan.getName());
        l.setHour(0);
        l.setDay(3);
        l.setLessonSubject(c0s3.getSubjectName());
        Created.getClasses().get(0).addLockedLesson(l);

        Lesson ll = new Lesson();
        ll.setTeacher(Daniel.getName());
        ll.setHour(0);
        ll.setDay(2);
        ll.setLessonSubject(c0s8.getSubjectName());
        Created.getClasses().get(0).addLockedLesson(ll);
        Created.initializeBreakFast();

        return Created;
    }
}
