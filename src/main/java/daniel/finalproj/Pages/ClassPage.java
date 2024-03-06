package daniel.finalproj.Pages;

import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import daniel.finalproj.Objects.Principle;
import daniel.finalproj.Objects.School;
import daniel.finalproj.Objects.SchoolClass;
import daniel.finalproj.Objects.Subject;
import daniel.finalproj.Servieses.PrincipleServies;
@Route("/ClassPage")
@PageTitle("ClassPage")
public class ClassPage extends VerticalLayout{
    PrincipleServies ps;
    String realname;
    String ClassName;
    School ThisSchool;
    Principle ThisPrinciple;
    String Proffesion;
    SchoolClass This;
    public ClassPage(PrincipleServies p)
    {
        this.ps = p;
        realname = (String) VaadinSession.getCurrent().getSession().getAttribute("RealName");
        Proffesion = (String) VaadinSession.getCurrent().getSession().getAttribute("proffesion");
        ClassName = (String) VaadinSession.getCurrent().getSession().getAttribute("ClassName");
        ThisPrinciple = ps.findbyname(realname);
        ThisSchool = ThisPrinciple.getSchool();
        This = ThisSchool.getClasses().get(ThisSchool.getClassIndexByName(ClassName));
        if(!Proffesion.equals("Principle"))
        {
            routeToPage("/");
        }
        else
        {
            H1 heather = new H1("Welcome To Class : "+This.getClassName());
            VerticalLayout vl = createSubjectLayout();
            add(heather,vl);
        }

    }

        public VerticalLayout createSubjectLayout() {
        VerticalLayout v1 = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        Button editSubjectbtn = new Button("Edit", e -> editsubject());
        Button RemoveSubjectButton = new Button("Remove Subject ", e -> removeSubject());
        Button addSubjectButton;
        // TODO add a dialog that create a teacher that exists in the system and adds
        Dialog dialog = new Dialog("Create A New Subject");

        VerticalLayout dialogLayout = createAddSubejctDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createSubjectSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(cancelButton);
        dialog.getFooter().add(saveButton);

        addSubjectButton = new Button("Add Subject", e -> dialog.open());

        buttons.add(addSubjectButton, RemoveSubjectButton, editSubjectbtn);
        Grid<Subject> grid = new Grid<>(Subject.class, false);
        grid.addColumn(Subject::getSubjectName).setHeader("SubjectName");
        grid.addColumn(Subject::getTeachersName).setHeader("Teacher");
        grid.addColumn(Subject::getWeeklyHours).setHeader("Weekly Hours");
        grid.addColumn(Subject::getPriority).setHeader("Priority");
        grid.addItemClickListener(event -> SubjctPressed(event.getItem()));

        List<Subject> Subjects = This.getSubjects();

        H3 h = new H3("Sybjects");
        grid.setItems(Subjects);
        v1.add(h, buttons, grid);

        return v1;
    }
    public void editsubject()
    {

    }
    public void removeSubject()
    {

    }
    public VerticalLayout createAddSubejctDialogLayout()
    {
        VerticalLayout vl = new VerticalLayout();

        return vl;
    }
    public Button createSubjectSaveButton(Dialog d)
    {
        Button b = new Button();

        return b;
    }
    public void SubjctPressed(Subject sub)
    {

    }

      private void routeToPage(String proffesion) {
      if (proffesion.equals("Teacher"))
         UI.getCurrent().getPage().setLocation("/Principle");
      else  if (proffesion.equals("Principle"))
         UI.getCurrent().getPage().setLocation("/Principle");
      else
         UI.getCurrent().getPage().setLocation("/SignUp");

   }
}
