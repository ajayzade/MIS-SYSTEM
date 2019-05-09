package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentPanel implements Initializable {

    @FXML
    TextField rollNo;

    @FXML
    PasswordField password;

    @FXML
    ComboBox combo;

    @FXML
    Label infoLabel,markLabel;

    @FXML
    ImageView profile;

    ObservableList<String> list = FXCollections.observableArrayList("five","six","seven","eight","nine","ten");

    public void logIn(ActionEvent actionEvent) throws SQLException {
        try {
            int roll = Integer.parseInt(rollNo.getText());
            String pass = password.getText();
            String clasa = combo.getValue().toString();
            String query1 = "select * from Student where roll=? && pass=?";
            ConnectDatabase connectDatabase = new ConnectDatabase();
            Connection connection = connectDatabase.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, roll);
            preparedStatement.setString(2, pass);
            ResultSet rs1 = preparedStatement.executeQuery();
            while (rs1.next()) {
                infoLabel.setText("NAME : " + rs1.getString("name") + "\n");
                infoLabel.setText(infoLabel.getText() + "\n" + "DATE OF BIRTH : " + rs1.getString("dob"));
                byte[] bytes = rs1.getBytes("image");
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                Image image = new javafx.scene.image.Image(bis);
                profile.setImage(image);
            }
            String query2 = "select * from " + clasa + " where id=?";
            Connection connection1 = connectDatabase.getConnect();
            PreparedStatement preparedStatement1 = connection1.prepareStatement(query2);
            preparedStatement1.setInt(1, roll);
            ResultSet rs2 = preparedStatement1.executeQuery();
            while (rs2.next()) {
                markLabel.setText("English : " + rs2.getInt("english") + "\n");
                markLabel.setText(markLabel.getText() + "Mathematics : " + rs2.getInt("maths") + "\n");
                markLabel.setText(markLabel.getText() + "Mathematics : " + rs2.getInt("science") + "\n");
                markLabel.setText(markLabel.getText() + "Mathematics : " + rs2.getInt("hindi") + "\n");
                markLabel.setText(markLabel.getText() + "Mathematics : " + rs2.getInt("social") + "\n");
                markLabel.setText(markLabel.getText() + "Mathematics : " + rs2.getInt("environment") + "\n");
            }
            preparedStatement.close();
            preparedStatement1.close();
            rs1.close();
            rs2.close();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        combo.setItems(list);
    }
}
