package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class FacultyPanel implements Initializable {

    @FXML
    TextField roll,eng,math,sci,sst,evs,hin;

    @FXML
    ComboBox combo1;

    @FXML
    PasswordField password;

    ObservableList<String> list = FXCollections.observableArrayList("five","six","seven","eight","nine","ten");
    String cl,pass;
    int id,english,maths,science,social,hindi,environment;

    public void logIn(ActionEvent actionEvent) {
        try {
            cl = combo1.getValue().toString();
            pass = password.getText();
            String query = "select * from faculty where alloted=? && pass=?";
            ConnectDatabase connectDatabase = new ConnectDatabase();
            Connection connection = connectDatabase.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,cl);
            preparedStatement.setString(2,pass);
            preparedStatement.execute();
            roll.setEditable(true);
            eng.setEditable(true);
            math.setEditable(true);
            sci.setEditable(true);
            sst.setEditable(true);
            evs.setEditable(true);
            hin.setEditable(true);
            combo1.setEditable(true);
            password.setEditable(true);
            preparedStatement.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setContentText("You Logged In Successfully");
            alert.show();
            password.setText("");
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errror");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void reset(ActionEvent actionEvent) {
        clear();
    }

    public void submit(ActionEvent actionEvent) {
        try{
            id = Integer.parseInt(roll.getText());
            english = Integer.parseInt(eng.getText());
            maths = Integer.parseInt(math.getText());
            science = Integer.parseInt(sci.getText());
            social = Integer.parseInt(sst.getText());
            hindi = Integer.parseInt(hin.getText());
            environment = Integer.parseInt(evs.getText());
            String query = "insert into " + cl + " (id, english, maths, science, hindi, social, environment) values (?,?,?,?,?,?,?)";
            ConnectDatabase connectDatabase = new ConnectDatabase();
            Connection connection = connectDatabase.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.setInt(2,english);
            preparedStatement.setInt(3,maths);
            preparedStatement.setInt(4,science);
            preparedStatement.setInt(5,social);
            preparedStatement.setInt(6,hindi);
            preparedStatement.setInt(7,environment);
            preparedStatement.execute();
            preparedStatement.close();
            clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setContentText("Marks Entered Successfully");
            alert.show();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errror");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        combo1.setItems(list);
        roll.setEditable(false);
        eng.setEditable(false);
        math.setEditable(false);
        sci.setEditable(false);
        sst.setEditable(false);
        evs.setEditable(false);
        hin.setEditable(false);
        combo1.setEditable(false);
    }

    public void clear(){
        roll.setText("");
        eng.setText("");
        math.setText("");
        sci.setText("");
        sst.setText("");
        evs.setText("");
        hin.setText("");
        password.setText("");
    }
}
