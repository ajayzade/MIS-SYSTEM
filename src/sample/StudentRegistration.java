package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class StudentRegistration implements Initializable {

    ObservableList<Integer> list1 = FXCollections.observableArrayList(5,6,7,8,9,10,11,12,13,14,15,16,17,18);
    ObservableList<String> list2 = FXCollections.observableArrayList("Male","Female","Other");
    ObservableList<String> list3 = FXCollections.observableArrayList("five","six","seven","eight","nine","ten");

    @FXML
    TextField first,middle,last,locality,category,caste,roll,fees;

    @FXML
    TextArea address;

    @FXML
    PasswordField pass;

    @FXML
    DatePicker dob;

    @FXML
    ComboBox comboage,combogender,Clas;

    @FXML
    ImageView photo;

    String fullName,city,Class,cate,cast,add,date,age,gender,password;

    Stage stage = new Stage();

    ConnectDatabase connectDatabase = new ConnectDatabase();

    int fee,rollNumber;
    InputStream fin;
    File file;

    public void browseFiles(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images","*.png","*.jpg"));
        file = fileChooser.showOpenDialog(stage);
        FileInputStream inputStream = new FileInputStream(file);
        Image image = new Image(inputStream);
        photo.setImage(image);
        fin = new FileInputStream(file);
        System.out.println(file);
    }

    public void resetAll(ActionEvent actionEvent) {
        clear();
    }

    public void submitInfo(ActionEvent actionEvent) {
        try {
            int flag = 0;
            fullName = first.getText() + " " + middle.getText() + " " + last.getText();
            city = locality.getText();
            Class = Clas.getValue().toString();
            cate = category.getText();
            cast = caste.getText();
            add = address.getText();
            rollNumber = Integer.parseInt(roll.getText());
            date = dob.getValue().toString();
            age = comboage.getValue().toString();
            gender = combogender.getValue().toString();
            fee = Integer.parseInt(fees.getText());
            password = pass.getText();
            try {
                if (fullName == "  " || city.isEmpty() || Class.isEmpty() || cate.isEmpty() || cast.isEmpty() ||
                        add.isEmpty() || date.isEmpty() || age.isEmpty() || gender.isEmpty() || roll.getText().isEmpty()) {
                    flag = 1;
                }
                if (flag == 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning");
                    alert.setContentText("Please Fill up all the details properly first..." + "\n");
                    alert.show();
                }else{
                    String query = "INSERT INTO Student (name, locality, class, dob, category, caste, fees, pass, gender, roll, address, image) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    try {
                        Connection connection = connectDatabase.getConnect();
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1,fullName);
                        statement.setString(2,city);
                        statement.setString(3,Class);
                        statement.setString(4,date);
                        statement.setString(5,cate);
                        statement.setString(6,cast);
                        statement.setInt(7,fee);
                        statement.setString(8,password);
                        statement.setString(9,gender);
                        statement.setInt(10,rollNumber);
                        statement.setString(11,add);
                        statement.setBinaryStream(12,fin,file.length());
                        statement.execute();
                        clear();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Info");
                        alert.setContentText("Your Registration has been done successfully");
                        alert.show();
                        statement.close();
                    }catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Warning");
                        alert.setContentText(e.getMessage());
                        alert.show();
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboage.setItems(list1);
        comboage.setValue(5);
        combogender.setItems(list2);
        combogender.setValue("Male");
        Clas.setItems(list3);
        Clas.setValue("five");
    }

    public void clear(){
        first.setText("");
        middle.setText("");
        last.setText("");
        locality.setText("");
        category.setText("");
        caste.setText("");
        address.setText("");
    }
}
