package daniel.finalproj.Pages;

import java.util.Date;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import daniel.finalproj.Objects.Principle;
import daniel.finalproj.Objects.Teacher;
import daniel.finalproj.Objects.User;
import daniel.finalproj.Servieses.PrincipleServies;
import daniel.finalproj.Servieses.UserServies;

/**
 * HomePage is a Vaadin page to let users to log-in this Chat App.
 * This page uses User-Session & Cookies to mange Authorization chat login.
 * 
 * @author ILAN PERETZ | 27.11.2023
 */

@Route("/SignUp")
@PageTitle("SignUp")
public class SignUpPage extends VerticalLayout {
   // chat-image for home page
   private String userName, password;
   private UserServies userServies;
   private PrincipleServies principleServies;
   /**
    * WelcomePage (home page) constructor.
    */
   public SignUpPage(UserServies us,PrincipleServies ps) {
      this.userServies = us;
      this.principleServies = ps;
      System.out.println("\n=======> HomePage('/') constructor started...\n");

      // Get from Session the user-session-id & 'username' attribute
      userName = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
      password = (String) VaadinSession.getCurrent().getSession().getAttribute("password");

      // if no 'username' attribute, this is a Guest.
      String welcomeMsg = "Sign Up Page : ";
      if (userName != null)
         routeToPrinciplePage();

      // create image for chat page

      // Setup & Arrange UI components on this page
      Span textDate = new Span(new Date() + "");
      textDate.getStyle().setColor("blue");
      // textDate.getStyle().set("background-color", "#F00");

      // getStyle().setBackground("yellow");

      add(textDate);
      add(new H1(welcomeMsg));
      add(creatSignUpPanel()); // login field & buttons

      // set all components in the Center of page
      setSizeFull();
      setAlignItems(Alignment.CENTER);

      System.out.println("\n=======> HomePage('/') constructor ends!\n");
   }

   // create a panel with some login field & buttons.
   private VerticalLayout creatSignUpPanel() {
      VerticalLayout panel = new VerticalLayout();
      if (userName == null) {
         // This is a Guest - let him fill name-field & click a login-button the enter
         // the chat.
         // The name MUST be two-words & one space between & with 4-15 letters.
         TextField fieldName = new TextField("Your UserName");
         fieldName.setPlaceholder("Enter your Username");
         fieldName.setHelperText("This will be your Username");
         fieldName.setRequiredIndicatorVisible(true);
         fieldName.setErrorMessage("Name MUST be 4-15 Letters and numbers, no spaces and signs ");
         fieldName.setAllowedCharPattern("[a-zA-Z0-9 ]"); // only letters & spaces
         fieldName.setPattern("\\w+"); // regx for two-words & one space between.
         fieldName.setMinLength(4); // min 4 chars
         fieldName.setMaxLength(15); // max 15 chars
         fieldName.setPrefixComponent(VaadinIcon.USER.create()); // add user icon
         fieldName.setClearButtonVisible(true); // fast clear text (x)
         fieldName.setValueChangeMode(ValueChangeMode.LAZY); // eed for ChangeListener.

         PasswordField FieldPassword = new PasswordField("Your password");
         FieldPassword.setPlaceholder("Enter your password");
         FieldPassword.setHelperText("Enter the password you set up when signing up");
         FieldPassword.setRequiredIndicatorVisible(true);
         FieldPassword.setErrorMessage(
               "password MUST be with one word, no space between, 4-15 Letters and numbers no spaces and signs!");
         FieldPassword.setAllowedCharPattern("[a-zA-Z0-9 ]"); // only letters & spaces
         FieldPassword.setPattern("\\w+"); // regx for onr word & one space between.
         FieldPassword.setMinLength(4); // min 4 chars
         FieldPassword.setMaxLength(15); // max 15 chars
         FieldPassword.setPrefixComponent(VaadinIcon.LOCK.create()); // add user icon
         FieldPassword.setClearButtonVisible(true); // fast clear text (x)
         FieldPassword.setValueChangeMode(ValueChangeMode.LAZY); // eed for ChangeListener.

         PasswordField FieldPassword2 = new PasswordField("Re-Enter the password");
         FieldPassword2.setPlaceholder("Enter your password again");
         FieldPassword2.setHelperText("Enter the password for the secend time");
         FieldPassword2.setRequiredIndicatorVisible(true);
         FieldPassword2.setErrorMessage(
               "password MUST be with one word, no space between, 4-15 Letters and numbers no spaces and signs!");
         FieldPassword2.setAllowedCharPattern("[a-zA-Z0-9 ]"); // only letters & spaces
         FieldPassword2.setPattern("\\w+"); // regx for onr word & one space between.
         FieldPassword2.setMinLength(4); // min 4 chars
         FieldPassword2.setMaxLength(15); // max 15 chars
         FieldPassword2.setPrefixComponent(VaadinIcon.LOCK.create()); // add user icon
         FieldPassword2.setClearButtonVisible(true); // fast clear text (x)
         FieldPassword2.setValueChangeMode(ValueChangeMode.LAZY); // eed for ChangeListener.

         TextField fieldRealName = new TextField("Your Real Name");
         fieldRealName.setPlaceholder("Enter your Real name");
         fieldRealName.setHelperText("with that name we will reference you");
         fieldRealName.setRequiredIndicatorVisible(true);
         fieldRealName.setErrorMessage("Name MUST be with two words, one space between, 4-15 Letters!");
         fieldRealName.setAllowedCharPattern("[a-zA-Z ]"); // only letters & spaces
         fieldRealName.setPattern("\\w+\\s\\w+"); // regx for two-words & one space between.
         fieldRealName.setMinLength(4); // min 4 chars
         fieldRealName.setMaxLength(15); // max 15 chars
         fieldRealName.setPrefixComponent(VaadinIcon.USER.create()); // add user icon
         fieldRealName.setClearButtonVisible(true); // fast clear text (x)
         fieldRealName.setValueChangeMode(ValueChangeMode.LAZY); // eed for ChangeListener.

         String[] s = new String[2];
         s[0] = "Teacher";
         s[1] = "Principle";
         ComboBox<String> comboBox = new ComboBox<>("Proffesion");
         comboBox.setItems(s);

         Button btnLogin = new Button("Sign Up",
               e -> SignUp(fieldName.getValue(), FieldPassword.getValue(), FieldPassword2.getValue(),
                     comboBox.getValue(), fieldRealName.getValue()));
         btnLogin.setEnabled(false);
         btnLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

         // when user type his name, check name validation.
         fieldName.addValueChangeListener(e -> btnLogin.setEnabled(!fieldName.isInvalid()));

         HorizontalLayout lo = new HorizontalLayout();
         lo.add(fieldName, FieldPassword, FieldPassword2);
         HorizontalLayout lo2 = new HorizontalLayout();
         lo2.add(fieldRealName, comboBox, btnLogin);

         Button LoginBtn = new Button("I already Have an Account", e -> routeToLoginPage());
         LoginBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

         panel.add(lo, lo2, LoginBtn);
         panel.setAlignItems(Alignment.CENTER);
      } 

      return panel;

   }

   // Login to chat by create 'username' attribute in the User-Session-Cookie &
   // route to Chat Page
   private void SignUp(String UserName, String password, String password2, String Proffesion, String RealName) {
      User user = userServies.findByUsername(UserName);
      if (user == null) {
         if (password.equals(password2)) {
            User newuser = new User(UserName, password, Proffesion, RealName);
            userServies.insertUser(newuser);
            VaadinSession.getCurrent().getSession().setAttribute("username", UserName);
            VaadinSession.getCurrent().getSession().setAttribute("password", password);
            VaadinSession.getCurrent().getSession().setAttribute("proffesion", Proffesion);
            VaadinSession.getCurrent().getSession().setAttribute("RealName", RealName);
            if (Proffesion.equals("Teacher")) {
               Teacher newTeacher = new Teacher(RealName);
               //teacherServies.insertTeacher(newTeacher);
               routeToTeacherPage();
            } else {
               Principle newPrincipal = new Principle(RealName); 
               principleServies.InsertPrincple(newPrincipal);           
               routeToPrinciplePage();
            }
         } else {
            Notification.show("passwords dosnt mutch");
         }

      } else {
         Notification.show("UserName already exist");
      }
   }

   // Logout from chat by Invalidate the User-Session
   private void logoutChat() {
      // Invalidate Session (delete the user-session-id and all its attributes)
      VaadinSession.getCurrent().getSession().invalidate();

      // Reload this page with new user-session-id
      UI.getCurrent().getPage().reload();
   }

   // Route to Chat Page ('/chat')
   private void routeToPrinciplePage() {
      UI.getCurrent().getPage().setLocation("/Principle");
   }

   private void routeToLoginPage() {
      UI.getCurrent().getPage().setLocation("/");
   }

   private void routeToTeacherPage() {
      UI.getCurrent().getPage().setLocation("/TeacherPage");
   }

}
