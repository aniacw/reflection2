package University;

import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private List<Person> personList;

    public DataBase() {
        personList = new ArrayList<>();
    }

    public void add(Person person){
        personList.add(person);
    }

    public List<Person> getPersonList() {
        return personList;
    }
}
