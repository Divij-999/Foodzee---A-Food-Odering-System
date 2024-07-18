package Driver;

import java.sql.Connection;
import java.sql.DriverManager;

public class jdbc_driver {
    public static Connection con;

    public static void mainConnection() throws Exception {
        String driverName = "com.mysql.cj.jdbc.Driver";
        Class.forName(driverName);
        System.out.println("Driver Loaded Successfully");
        String dburl = "jdbc:mysql://localhost:3306/foodzee2";
        String dbuser = "root";
        String dbpass = "*****"; // Replace with your actual password
        con = DriverManager.getConnection(dburl, dbuser, dbpass);
        if (con != null) {
            System.out.println("Connection Successful");
        } else {
            System.out.println("Connection Failed");
        }
    }
}
