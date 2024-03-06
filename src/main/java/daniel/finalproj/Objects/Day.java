package daniel.finalproj.Objects;


public class Day {
    private String name;
    private Hour[] hours;
    public static final int MAX_HOUR = 9;
    public static final int STARTING_HOUR = 8;
    public static final int LUNCH_HOUR = 11;
    public Day()
    {
        
    }

    public Day(String name) {
        this.name = name;
        this.hours = new Hour[MAX_HOUR];
        initializehours();
    }
    public void initializehours(){
        for (int i = 0; i < MAX_HOUR ; i++) {
            hours[i] = new Hour();
        }
    }
    public Day(Day otherDay) {
        this.name = otherDay.name;
        this.hours = new Hour[otherDay.hours.length];
        for (int i = 0; i < otherDay.hours.length; i++) {
            if (otherDay.hours[i] != null) {
                this.hours[i] = new Hour(otherDay.hours[i]);
            }
        }
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hour[] getHours() {
        return hours;
    }

    public void setHours(Hour[] Hours) {
        this.hours = Hours;
    }


    // Optionally, you can override toString() to provide a meaningful string
    // representation
    @Override
    public String toString() {
        return "Day{" +
                "name='" + name + '\'' +
                ", hours=" + hours +
                '}';
    }
}
