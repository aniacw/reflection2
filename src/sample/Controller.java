package sample;

import University.DataBase;
import University.Person;
import University.Student;
import University.Teacher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import util.FieldInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Controller {
    private Person person;
    DataBase dataBase = new DataBase();
    private static final String packagePrefix = "University.";

    Constructor studCtor;
    Constructor teachCtor;

    @FXML
    Button addToDatabase;

    @FXML
    Label listOfPersons;

    @FXML
    Button displayFields;

    @FXML
    Button create;

    @FXML
    private ComboBox<Class> personType;

    @FXML
    ListView<Person> personListView;

    @FXML
    VBox fieldVBox;

    public Controller() throws ClassNotFoundException, NoSuchMethodException {
        studCtor = Student.class.getConstructor();
        teachCtor= Teacher.class.getConstructor();
//
//        List<Field> studentFieldsList = new ArrayList<>();
//        studentFieldsList.Arrays.asList(Student.class.getDeclaredFields());
//        studentFieldsList.addAll(new ArrayList<Field>(Person.class.getDeclaredFields()));
    }


    @FXML
    public void initialize() {
        //person.getClass().getSimpleName();

        personType.getItems().add(Student.class);
        personType.getItems().add(Teacher.class);
    }

    public void onButtonAddToDatabaseClicked(ActionEvent actionEvent) {
        ////

    }

    @FXML
    public void onButtonDisplayFieldsClicked(ActionEvent actionEvent) {

    }

    public void onPersonTypeSelected(ActionEvent actionEvent) {
        Class c = personType.getValue();
        if (c == null)
            return;
        Map<String, FieldInfo> fieldsInfo = util.Dictionary.memberInfoDictionary(c);
        fieldVBox.getChildren().clear();
        for (FieldInfo f : fieldsInfo.values()) {
            TextField newField = new TextField();
            newField.setText(f.field.getName());
            fieldVBox.getChildren().add(newField);
        }
    }
}
