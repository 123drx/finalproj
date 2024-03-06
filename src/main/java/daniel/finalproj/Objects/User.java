package daniel.finalproj.Objects;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "Users")
public class User {
    @Id
    private String username;
    private String password;
    private String realName;
    private String proffesion;

    public User()
    {
        
    }
    public User(String UserName,String Password)
    {
        this.password = Password;
        this.username = UserName;
    }
    public User(String UserName,String Password,String proffesion,String Realname)
    {
        this.password = Password;
        this.username = UserName;
        this.proffesion = proffesion;
        this.realName = Realname;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String userName) {
        this.username = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getProffesion() {
        return proffesion;
    }
    public void setProffesion(String proffesion) {
        this.proffesion = proffesion;
    }

    
    
}
