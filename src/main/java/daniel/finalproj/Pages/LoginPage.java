package daniel.finalproj.Pages;

import java.util.Date;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
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

import daniel.finalproj.Objects.User;
import daniel.finalproj.Servieses.UserServies;



@Route("/")
@PageTitle("Home")
public class LoginPage extends VerticalLayout {
   // chat-image for home page
   private static final String CHAT_IMAGE_URL = "https://rb.gy/vi7fim";
   private String sessionId, userName, password;
   private UserServies userServies;

   /**
    * WelcomePage (home page) constructor.
    */
   public LoginPage(UserServies us) {
      this.userServies = us;
      System.out.println("\n=======> HomePage('/') constructor started...\n");

      // Get from Session the user-session-id & 'username' attribute
      sessionId = VaadinSession.getCurrent().getSession().getId();
      userName = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
      password = (String) VaadinSession.getCurrent().getSession().getAttribute("password");

      // if no 'username' attribute, this is a Guest.
      String welcomeMsg = "Welcome Guest!";
      if (userName != null)
         welcomeMsg = "Welcome " + userName.toUpperCase();

      // create image for chat page
      Image imgChat = new Image(CHAT_IMAGE_URL, "chat image");
      imgChat.setHeight("200px");

      // Setup & Arrange UI components on this page
      Span textDate = new Span(new Date() + "");
      textDate.getStyle().setColor("blue");
      // textDate.getStyle().set("background-color", "#F00");

      // getStyle().setBackground("yellow");

      add(textDate);
      add(imgChat);
      add(new H1(welcomeMsg));
      //add(new H3("( SessionID: " + sessionId + " )"));
      add(createLoginPanel()); // login field & buttons

      // set all components in the Center of page
      setSizeFull();
      setAlignItems(Alignment.CENTER);

      System.out.println("\n=======> HomePage('/') constructor ends!\n");
   }

   // create a panel with some login field & buttons.
   private VerticalLayout createLoginPanel() {
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

        Button btnLogin = new Button("Login Chat", e -> loginChat(fieldName.getValue(), FieldPassword.getValue()));
        btnLogin.setEnabled(false);
        btnLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // when user type his name, check name validation.
        fieldName.addValueChangeListener(e -> btnLogin.setEnabled(!fieldName.isInvalid()));

        Button btnRegister = new Button("I Dont Have an Account", e -> routeToSignUpPage());
        btnRegister.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        HorizontalLayout fieldsLayout = new HorizontalLayout(fieldName, FieldPassword, btnLogin);
        fieldsLayout.setAlignItems(Alignment.CENTER);
        fieldsLayout.setSpacing(true);

        panel.add(fieldsLayout, btnRegister);
        panel.setAlignItems(Alignment.CENTER);
    } else {
        Button btnGotoChat = new Button("Back to Page",
                e -> routeToPage(userServies.findByUsername(userName).getProffesion()));
        btnGotoChat.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnLogout = new Button("Logout", VaadinIcon.SIGN_OUT.create(), e -> logoutChat());
        btnLogout.setIconAfterText(true);
        btnLogout.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR); // RED button

        panel.add(btnGotoChat, btnLogout);
        panel.setAlignItems(Alignment.CENTER);
    }

    return panel;
}


   // Login to chat by create 'username' attribute in the User-Session-Cookie &
   // route to Chat Page
   private void loginChat(String UserName, String password) {
      User user = userServies.findByUsername(UserName);
      if (user != null) {
         if (user.getPassword().equals(password)) {
            VaadinSession.getCurrent().getSession().setAttribute("username", UserName);
            VaadinSession.getCurrent().getSession().setAttribute("password", password);
            VaadinSession.getCurrent().getSession().setAttribute("proffesion", user.getProffesion());
            VaadinSession.getCurrent().getSession().setAttribute("RealName", user.getRealName());
            routeToPage(user.getProffesion());
         } else {
            Notification.show("Password are Incorrect");
         }
      } else {
         Notification.show("Userdosnt exist");
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
   private void routeToPage(String proffesion) {
      if (proffesion.equals("Teacher"))
         UI.getCurrent().getPage().setLocation("/Principle");
      else  if (proffesion.equals("Principle"))
         UI.getCurrent().getPage().setLocation("/Principle");
      else
         UI.getCurrent().getPage().setLocation("/SignUp");

   }

   private void routeToSignUpPage() {
         UI.getCurrent().getPage().setLocation("/SignUp");
   }

}
