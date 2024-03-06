package daniel.finalproj.Objects;


public class Constrains {

    private int StartHour;
    private int EndHour;

    public Constrains()
    {

    }
    
    public Constrains(int Start , int End)
    {
            this.StartHour = Start;
            this.EndHour = End;
    }
    
    public int getStartHour() {
        return StartHour;
    }
    
    public void setStartHour(int startHour) {
        StartHour = startHour;
    }
    
    public int getEndHour() {
        return EndHour;
    }
    
    public void setEndHour(int endHour) {
        EndHour = endHour;
    }
    
    public void SetConstrains(int StartHour , int EndHour)
    {
        this.StartHour = StartHour;
        this.EndHour = EndHour;
    }

    
    
}
