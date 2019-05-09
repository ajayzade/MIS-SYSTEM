package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginAdmin implements Initializable {

    @FXML
    TextField user;

    @FXML
    PasswordField pass;

    @FXML
    Button cancel,login;

    String username,password,u,p;

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void login(ActionEvent actionEvent) throws SQLException {
        int flag = 0;
        username = user.getText();
        password = pass.getText();
        if(username.isEmpty() || password.isEmpty()){
            flag = 1;
        }
        if (flag == 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill the fields first ");
            alert.show();
        }else {
            ConnectDatabase connectDatabase = new ConnectDatabase();
            Connection connection = connectDatabase.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from adminlogin");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                u = resultSet.getString("username");
                p = resultSet.getString("password");
            }
            if (username.equals(u) && password.equals(p)){
                Stage stage = new Stage();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("admin login.fxml"));
                    Scene scene = new Scene(root);
                    stage.setTitle("Admin Panel");
                    stage.setScene(scene);
                    stage.show();
                    Stage stage1 = (Stage) login.getScene().getWindow();
                    stage1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Incorrect Username or Password");
                alert.show();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
