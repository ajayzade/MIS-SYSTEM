package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDatabase {
    public Connection getConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school","root","");
            System.out.println("Connected");
            return conn;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Not Connected");
        return null;
    }
}
