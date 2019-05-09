package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    ImageView sliderImage;

    String[] s = new String[] {
            "C:\\\\Users\\\\Lenovo\\\\IntelliJIDEAProjects\\\\School Software\\\\src\\\\images\\\\one.png",
            "C:\\\\Users\\\\Lenovo\\\\IntelliJIDEAProjects\\\\School Software\\\\src\\\\images\\\\two.jpg",
            "C:\\\\Users\\\\Lenovo\\\\IntelliJIDEAProjects\\\\School Software\\\\src\\\\images\\\\three.jpg",
            "C:\\\\Users\\\\Lenovo\\\\IntelliJIDEAProjects\\\\School Software\\\\src\\\\images\\\\four.jpg",
            "C:\\\\Users\\\\Lenovo\\\\IntelliJIDEAProjects\\\\School Software\\\\src\\\\images\\\\five.jpg"
    };

    Timer tm;
    int x = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setImage(x);
        tm = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                x = x + 1;
                if(x >= s.length){
                    x = 0;
                }
                setImage(x);
            }
        });
        tm.start();
        ConnectDatabase connectDatabase = new ConnectDatabase();
        connectDatabase.getConnect();
    }

    public void adLogin(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("login admin.fxml"));
            stage.setTitle("Admin Panel");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void faLogin(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("faculty panel.fxml"));
            stage.setTitle("Faculty Panel");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stuLogin(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("student panel.fxml"));
            stage.setTitle("Student Panel");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stuRegistration(ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("student registration.fxml"));
            primaryStage.setTitle("Student Registration");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void faRegistration(ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("faculty registration.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Faculty Registration");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void setImage(int i){
        try {
            FileInputStream inputStream = new FileInputStream(s[i]);
            Image image = new Image(inputStream);
            sliderImage.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
