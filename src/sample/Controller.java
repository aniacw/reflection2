package sample;

import University.DataBase;
import University.Person;
import University.Student;
import University.Teacher;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import util.Dictionary;
import util.FieldInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
    Button update;

    @FXML
    private ComboBox<Class> personType;

    @FXML
    ListView<Person> personListView;

    @FXML
    VBox fieldVBox;

    @FXML
    VBox fieldLabelVBox;

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


    public void onPersonTypeSelected(ActionEvent actionEvent) {
        if (personType.getSelectionModel().getSelectedItem() != null)
            personListView.getSelectionModel().clearSelection();
        Class c = personType.getValue();
        if (c == null)
            return;
        Map<String, FieldInfo> fieldsInfo = util.Dictionary.memberInfoDictionary(c);
        fieldVBox.getChildren().clear();
        fieldLabelVBox.getChildren().clear();
        for (FieldInfo f : fieldsInfo.values()) {
            TextField newField = new TextField();
            newField.setPromptText(f.field.getName());
            newField.setPrefHeight(30);
            newField.setId(f.field.getName());
            fieldVBox.getChildren().add(newField);
            Label label = new Label(f.field.getName());
            label.setPrefHeight(30);
            fieldLabelVBox.getChildren().add(label);
        }

    }


    private void updatePersonData(Person person){
        try {
            Map<String, FieldInfo> fieldsInfo = util.Dictionary.memberInfoDictionary(person.getClass());
            for(FieldInfo f : fieldsInfo.values()) {
                String textBoxValue = getTextBoxValue(f.field.getName());
                if (textBoxValue != null) {
                    f.setter.invoke(person, textBoxValue);
                }
            }
        } catch ( IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void onButtonUpdateClicked(ActionEvent actionEvent) {
        Person selectedPerson = personListView.getSelectionModel().getSelectedItem();
        if (selectedPerson == null) {
            Class type = personType.getValue();
            try {
                if (!checkIfRobot())
                    return;
                Person person = (Person) type.getConstructor().newInstance();
                updatePersonData(person);
                dataBase.add(person);
                personListView.getItems().add(person);
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        else{
            updatePersonData(selectedPerson);
        }

//
//        switch (personTypeString) {
//            case "Student":
//                Student student = new Student();
//                try {
//                    student.setYear();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                student.setId();
//                student.setName();
//                Alert.display("Revise your input", "Make sure you've got all the fields filled in");
//                checkIfRobot();
//                dataBase.add(student);
//                break;
//
//            case "Teacher":
//                Teacher teacher = new Teacher();
//                teacher.setSubject();
//                teacher.setId();
//                teacher.setName();
//                Alert.display("Revise your input", "Make sure you've got all the fields filled in");
//                checkIfRobot();
//                dataBase.add(teacher);
//                break;
//        }
    }

    private boolean checkIfRobot() {
        if (notRobot.isSelected() == false) {
            Alert.display("Warning", "Confirm you're not a robot");
            return false;
        }
        else
            return true;
    }


    private String getTextBoxValue(String fieldName) {
        String textFieldId;
        TextField textField;
        ObservableList<Node> observableList = fieldVBox.getChildren();
        for (Node n : observableList) {
            textField = (TextField)n;
            textFieldId = n.getId();
            if(textFieldId.equals(fieldName)){
                return textField.getText();
            }
        }
        return null;
    }

    public void onPersonListViewClicked(MouseEvent mouseEvent) {
        if (personListView.getSelectionModel().getSelectedItem() != null)
            personType.getSelectionModel().clearSelection();
        Person selectedPerson = personListView.getSelectionModel().getSelectedItem();
        Class type = selectedPerson.getClass();

        Map<String, FieldInfo> fieldsInfo = util.Dictionary.memberInfoDictionary(type);
        fieldVBox.getChildren().clear();
        fieldLabelVBox.getChildren().clear();
        for (FieldInfo f : fieldsInfo.values()) {
            TextField newField = new TextField();
            newField.setPromptText(f.field.getName());
            try {
                newField.setText((String)f.getter.invoke(selectedPerson));
            } catch (IllegalAccessException | InvocationTargetException e) {
                newField.setText("???");
            }
            newField.setPrefHeight(30);
            newField.setId(f.field.getName());
            fieldVBox.getChildren().add(newField);
            Label label = new Label(f.field.getName());
            label.setPrefHeight(30);
            fieldLabelVBox.getChildren().add(label);
        }
    }
}
