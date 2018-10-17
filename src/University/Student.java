package University;

public class Student extends Person {

    private String year;

    public Student() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        //if (year < 0 || year > 2500)
         //   throw new Exception("invalid year");
        this.year = year;
    }
}
