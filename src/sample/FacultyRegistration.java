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

public class FacultyRegistration implements Initializable {
    ObservableList<Integer> list1 = FXCollections.observableArrayList(25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58);
    ObservableList<String> list2 = FXCollections.observableArrayList("Male","Female","Other");
    ObservableList<String> list3 = FXCollections.observableArrayList("five","six","seven","eight","nine","ten");

    @FXML
    TextField first,middle,last,locality,Quali,fees;

    @FXML
    TextArea address;

    @FXML
    DatePicker dob;

    @FXML
    ComboBox comboage,combogender,classAlloted;

    @FXML
    ImageView photo;

    String fullName,city,password,clalloted,qualification,add,date,gender;

    Stage stage = new Stage();
    int age,fee;
    File file;
    InputStream fin;

    @FXML
    PasswordField pass;

    public void submitInfo(ActionEvent actionEvent) {
        try {
            int flag = 0;
            fullName = first.getText() + " " + middle.getText() + " " + last.getText();
            city = locality.getText();
            clalloted = classAlloted.getValue().toString();
            qualification = Quali.getText();
            add = address.getText();
            date = dob.getValue().toString();
            age = Integer.parseInt(comboage.getValue().toString());
            gender = combogender.getValue().toString();
            fee = Integer.parseInt(fees.getText());
            password = pass.getText();
            try {
                if (fullName == "  " || city.isEmpty() || clalloted.isEmpty() || qualification.isEmpty() ||
                        add.isEmpty() || date.isEmpty() || gender.isEmpty()) {
                    flag = 1;
                }
                if (flag == 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning");
                    alert.setContentText("Please Fill up all the details properly first..." + "\n");
                    alert.show();
                }else{
                    String query = "INSERT INTO faculty (name, locality, age, qualification, dob, alloted, salary, gender, pass, address, image) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                    try{
                        ConnectDatabase connectDatabase = new ConnectDatabase();
                        Connection connection = connectDatabase.getConnect();
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1,fullName);
                        preparedStatement.setString(2,city);
                        preparedStatement.setInt(3,age);
                        preparedStatement.setString(4,qualification);
                        preparedStatement.setString(5,date);
                        preparedStatement.setString(6,clalloted);
                        preparedStatement.setInt(7,fee);
                        preparedStatement.setString(8,gender);
                        preparedStatement.setString(9,password);
                        preparedStatement.setString(10,add);
                        fin = new FileInputStream(file);
                        preparedStatement.setBinaryStream(11,fin,file.length());
                        preparedStatement.execute();
                        clear();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Info");
                        alert.setContentText("Your Registration has been done successfully");
                        alert.show();
                        preparedStatement.close();
                    }catch (Exception e){
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

    public void browseFiles(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images","*.png","*.jpg"));
        file = fileChooser.showOpenDialog(stage);
        FileInputStream inputStream = new FileInputStream(file);
        Image image = new Image(inputStream);
        photo.setImage(image);
        System.out.println(file);
    }

    public void resetAll(ActionEvent actionEvent) {
        clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboage.setItems(list1);
        comboage.setValue(25);
        combogender.setItems(list2);
        combogender.setValue("Male");
        classAlloted.setItems(list3);
        classAlloted.setValue("5th");
    }

    public void clear(){
        first.setText("");
        middle.setText("");
        last.setText("");
        locality.setText("");
        Quali.setText("");
        address.setText("");
    }
}
