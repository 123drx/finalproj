package daniel.finalproj.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Principles")
public class Principle {
    String name;
    School school ;
    public Principle()
    {

    }
    public Principle(String Name)
    {
        this.name = Name;
    }
    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = Name;
    }
    public School getSchool() {
        return school;
    }
    public void setSchool(School s) {
        this.school = s;
    }
    
    
}
