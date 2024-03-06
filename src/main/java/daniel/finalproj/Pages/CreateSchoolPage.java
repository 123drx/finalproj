package daniel.finalproj.Pages;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import daniel.finalproj.Objects.Principle;
import daniel.finalproj.Objects.School;
import daniel.finalproj.Servieses.PrincipleServies;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;

@Route("/CreateSchool")
@PageTitle("Create School Page")
public class CreateSchoolPage extends VerticalLayout {
        PrincipleServies principleServies;
        String proffesion;
        String realname;
        String userName;
        String password;
        Principle ThisPrinciple;

    private TextField schoolNameField;

    public CreateSchoolPage(PrincipleServies PrincipleServies) {
        this.principleServies = PrincipleServies;
        VerticalLayout vl = new VerticalLayout();
        proffesion = (String) VaadinSession.getCurrent().getSession().getAttribute("proffesion");
        realname = (String) VaadinSession.getCurrent().getSession().getAttribute("RealName");
        userName = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
        password = (String) VaadinSession.getCurrent().getSession().getAttribute("password");
        this.ThisPrinciple = principleServies.findbyname(realname);


        H2 Title = new H2("You Are Not Connected To A School Yet");
        Dialog dialog = new Dialog("Create A New School");
       // dialog.getElement().executeJs("this._primaryButton.textContent = 'mmm';");
        //dialog.setHeaderTitle("Create New School");

        VerticalLayout dialogLayout = createDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(cancelButton);
        dialog.getFooter().add(saveButton);

        Button button = new Button("Create School", e -> dialog.open());

        vl.add(Title,dialog, button);
        vl.setAlignItems(Alignment.CENTER);
        add(vl);
    }

    private VerticalLayout createDialogLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);

        // Initialize schoolNameField
        schoolNameField = new TextField("Add a School");
        layout.add(schoolNameField);

        return layout;
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Save", event -> {
            String schoolName = schoolNameField.getValue();
            School school = new School(schoolName);
            ThisPrinciple.setSchool(school);
            principleServies.UpdatePrinciple(ThisPrinciple);
            routeToSchoolPagePage();
            
            System.out.println("School Name entered: " + schoolName);
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

   
   private void routeToSchoolPagePage() {
    UI.getCurrent().getPage().setLocation("/Principle");
}

}
