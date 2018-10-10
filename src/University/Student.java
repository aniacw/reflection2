package University;

public class Student extends Person {

    private int year;

    public Student() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) throws Exception {
        if (year < 0 || year > 2500)
            throw new Exception("invalid year");
        this.year = year;
    }
}
