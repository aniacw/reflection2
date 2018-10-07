package sample;

import University.Person;
import University.Student;
import University.Teacher;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.lang.reflect.Field;
import java.util.Iterator;

public class Controller {
    private Person person;
    Student studentObj = new Student();
    Teacher teacherObj = new Teacher();
    Field[] fields;

    public void setAccessible() {
        for (Field f : fields)
            f.setAccessible(true);
    }

    @FXML
    private ComboBox<String> personType;

    @FXML
    public void initialize() {
        //person.getClass().getSimpleName();

        personType.getItems().add("Student");
        personType.getItems().add("Teacher");
    }

    @FXML
    public void displayFields() {
        if (personType.getValue().equals("Student")) {
            setAccessible();
            fields = studentObj.get


        }

    }


}
