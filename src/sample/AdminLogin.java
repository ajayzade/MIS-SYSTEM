package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminLogin implements Initializable {

    ObservableList<String> list = FXCollections.observableArrayList("five","six","seven","eight","nine","ten");

    @FXML
    TableView<Model1> table1;
    @FXML
    TableColumn<Model1,String> col_name1;
    @FXML
    TableColumn<Model1,String> col_locality1;
    @FXML
    TableColumn<Model1,String> col_class1;
    @FXML
    TableColumn<Model1,String> col_dob1;
    @FXML
    TableColumn<Model1,String> col_cate1;
    @FXML
    TableColumn<Model1,String> col_caste1;
    @FXML
    TableColumn<Model1,String> col_fee1;
    @FXML
    TableColumn<Model1,String> col_gender1;
    @FXML
    TableColumn<Model1,String> col_id1;
    @FXML
    TableColumn<Model1,String> col_add1;

    ObservableList<Model1> list1 = FXCollections.observableArrayList();

    @FXML
    TableView<Model2> table2;
    @FXML
    TableColumn<Model2,String> col_name2;
    @FXML
    TableColumn<Model2,String> col_locality2;
    @FXML
    TableColumn<Model2,String> col_age2;
    @FXML
    TableColumn<Model2,String> col_qualification2;
    @FXML
    TableColumn<Model2,String> col_dob2;
    @FXML
    TableColumn<Model2,String> col_alloted2;
    @FXML
    TableColumn<Model2,String> col_salary2;
    @FXML
    TableColumn<Model2,String> col_gender2;
    @FXML
    TableColumn<Model2,String> col_add2;

    ObservableList<Model2> list2 = FXCollections.observableArrayList();

    Connection connection;
    ConnectDatabase connectDatabase;

    //Code for Tab1

    public void load1(ActionEvent actionEvent) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Student");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list1.add(new Model1(resultSet.getString("name"),resultSet.getString("locality"),resultSet.getString("class"),
                        resultSet.getString("dob"),resultSet.getString("category"),resultSet.getString("caste"),
                        resultSet.getInt("fees"),resultSet.getString("gender"),resultSet.getInt("roll"),resultSet.getString("address")));
            }
            col_name1.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_locality1.setCellValueFactory(new PropertyValueFactory<>("locality"));
            col_class1.setCellValueFactory(new PropertyValueFactory<>("class"));
            col_dob1.setCellValueFactory(new PropertyValueFactory<>("dob"));
            col_cate1.setCellValueFactory(new PropertyValueFactory<>("category"));
            col_caste1.setCellValueFactory(new PropertyValueFactory<>("caste"));
            col_fee1.setCellValueFactory(new PropertyValueFactory<>("fees"));
            col_gender1.setCellValueFactory(new PropertyValueFactory<>("gender"));
            col_id1.setCellValueFactory(new PropertyValueFactory<>("roll"));
            col_add1.setCellValueFactory(new PropertyValueFactory<>("address"));
            table1.setItems(list1);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        combo.setValue("five");
        combo.setItems(list);
        connectDatabase = new ConnectDatabase();
        connection = connectDatabase.getConnect();
    }

    //Code for Tab2

    public void load2(ActionEvent actionEvent) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Faculty");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list2.add(new Model2(resultSet.getString("name"),resultSet.getString("locality"),resultSet.getInt("age"),
                        resultSet.getString("qualification"),resultSet.getString("dob"),resultSet.getString("alloted"),
                        resultSet.getInt("salary"),resultSet.getString("gender"),resultSet.getString("address")));
            }
            col_name2.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_locality2.setCellValueFactory(new PropertyValueFactory<>("locality"));
            col_age2.setCellValueFactory(new PropertyValueFactory<>("age"));
            col_qualification2.setCellValueFactory(new PropertyValueFactory<>("qualification"));
            col_dob2.setCellValueFactory(new PropertyValueFactory<>("dob"));
            col_alloted2.setCellValueFactory(new PropertyValueFactory<>("alloted"));
            col_salary2.setCellValueFactory(new PropertyValueFactory<>("salary"));
            col_gender2.setCellValueFactory(new PropertyValueFactory<>("gender"));
            col_add2.setCellValueFactory(new PropertyValueFactory<>("address"));
            table2.setItems(list2);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    //Code for Tab3

    @FXML
    Label tv;

    @FXML
    ImageView profile;

    int id;
    String Class;

    @FXML
    TextField ID;

    @FXML
    ComboBox combo;

    public void searchStudent(ActionEvent actionEvent) {
        try {
            id = Integer.parseInt(ID.getText());
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Student where roll=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                tv.setText("Name : " + resultSet.getString("name") + "\n");
                tv.setText(tv.getText() + "Locality  : " + resultSet.getString("locality") + "\n");
                tv.setText(tv.getText() + "Class  : " + resultSet.getString("class") + "\n");
                tv.setText(tv.getText() + "Date of Birth  : " + resultSet.getString("dob") + "\n");
                tv.setText(tv.getText() + "Category  : " + resultSet.getString("category") + "\n");
                tv.setText(tv.getText() + "Caste  : " + resultSet.getString("caste") + "\n");
                tv.setText(tv.getText() + "Fees Paid  : " + resultSet.getInt("fees") + "\n");
                tv.setText(tv.getText() + "Gender : " + resultSet.getString("gender") + "\n");
                tv.setText(tv.getText() + "Address  : " + resultSet.getString("address") + "\n");
                byte[] bytes = resultSet.getBytes("image");
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                Image image = new Image(bis);
                profile.setImage(image);
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void deleteStudent(ActionEvent actionEvent) {
        try {
            Class = combo.getValue().toString();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from faculty where alloted=?");
            preparedStatement.setString(1,Class);
            preparedStatement.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText("Information of student deleted successfully");
            alert.show();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void searchFaculty(ActionEvent actionEvent) {
        try {
            Class = combo.getValue().toString();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from faculty where alloted=?");
            preparedStatement.setString(1,Class);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                tv.setText("Name : " + resultSet.getString("name") + "\n");
                tv.setText(tv.getText() + "Locality  : " + resultSet.getString("locality") + "\n");
                tv.setText(tv.getText() + "Age : " + resultSet.getInt("age") + "\n");
                tv.setText(tv.getText() + "Qualification : " + resultSet.getString("qualification") + "\n");
                tv.setText(tv.getText() + "Date of Birth : " + resultSet.getString("dob") + "\n");
                tv.setText(tv.getText() + "Salary : " + resultSet.getInt("salary") + "\n");
                tv.setText(tv.getText() + "Gender : " + resultSet.getString("gender") + "\n");
                tv.setText(tv.getText() + "Address  : " + resultSet.getString("address") + "\n");
                byte[] bytes = resultSet.getBytes("image");
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                Image image = new Image(bis);
                profile.setImage(image);
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void deleteFaculty(ActionEvent actionEvent) {
        try {
            id = Integer.parseInt(ID.getText());
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from Student where roll=?");
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText("Information of student deleted successfully");
            alert.show();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
