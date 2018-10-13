package sample;

import University.DataBase;
import University.Person;
import University.Student;
import University.Teacher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import util.FieldInfo;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Controller {
    private Person person;
    DataBase dataBase = new DataBase();

    Constructor studCtor;
    Constructor teachCtor;

    @FXML
    Button displayDatabase;

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

    @FXML
    CheckBox notRobot;

    public Controller() throws ClassNotFoundException, NoSuchMethodException {
        studCtor = Student.class.getConstructor();
        teachCtor = Teacher.class.getConstructor();
    }

    @FXML
    public void initialize() {
        personType.getItems().add(Student.class);
        personType.getItems().add(Teacher.class);
    }

    public void onButtonDisplayDatabaseClicked(ActionEvent actionEvent) {
        for (Person d : dataBase.getPersonList())
            personListView.getItems().add(d);
    }

    @FXML
    public void onButtonDisplayFieldsClicked(ActionEvent actionEvent) {
        //
    }

    ArrayList<TextField> textFields = new ArrayList<>();

    public void onPersonTypeSelected(ActionEvent actionEvent) {
        Class c = personType.getValue();
        if (c == null)
            return;
        Map<String, FieldInfo> fieldsInfo = util.Dictionary.memberInfoDictionary(c);
        fieldVBox.getChildren().clear();

        for (FieldInfo f : fieldsInfo.values()) {
            TextField newField = new TextField();
            newField.setPromptText(f.field.getName());
            fieldVBox.getChildren().add(newField);
        }
    }

    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();

    public void onButtonCreateClicked(ActionEvent actionEvent) {
        String personTypeString = personType.getValue().toString();

        switch (personTypeString) {
            case "Student":
                Student student = new Student();
                try {
                    student.setYear(Integer.parseInt(input));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                student.setId(Integer.parseInt(input));
                student.setName(input);
                Alert.display("Revise your input", "Make sure you've got all the fields filled in");
                checkIfRobot();
                dataBase.add(student);
                break;

            case "Teacher":
                Teacher teacher = new Teacher();
                teacher.setSubject(input);
                teacher.setId(Integer.parseInt(input));
                teacher.setName(input);
                Alert.display("Revise your input", "Make sure you've got all the fields filled in");
                checkIfRobot();
                dataBase.add(teacher);
                break;
        }
    }

    public void checkIfRobot(){
        if(notRobot.isSelected() == false)
            Alert.display("Warning", "Confirm you're not a robot");
    }
}
