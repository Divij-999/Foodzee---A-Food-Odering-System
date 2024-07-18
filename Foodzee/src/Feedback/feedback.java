package Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import Driver.jdbc_driver;
import Login.foodzee_login;
import userMenu.SearchProduct;

public class feedback {

    static Scanner scanner = new Scanner(System.in);
    static Connection con = jdbc_driver.con;
    private static boolean choice = false;
    public static String comment;

    public static void mainFeedback()
    {
        System.out.println("Would you like to give feedback for your current orders\n yes \n no");
        String feedbackChoice = scanner.next();
            while (true) {
                if(feedbackChoice.equalsIgnoreCase("yes"))
                {
                    choice = true;
                    break;
                }
                else if(feedbackChoice.equalsIgnoreCase("no"))
                {
                    choice = false;
                    break;
                }
                System.out.println("Enter a valid choice (yes/no)");
            }
        if(choice)
        {
            //enter feedback data
            for(Integer currentOrderId : SearchProduct.orderIdCount)
            {
                orderFeedback(currentOrderId);
            }
            // addFeedback();
        }
        else{
            System.out.println("Thank you for ordering");
        }
    }
    private static void orderFeedback(int currentOrderId)
    {
        System.out.println("\nEnter rating w.r.t to stars\n");
        for(int i = 5 ; i>0 ; i--)
        {
            System.out.print(i+") ");
            for(int j = 1 ; j<=i ; j++)
            {
                System.out.print("*");
            }
            System.out.println("");
        }
        int ratingChoice = 0;

            while (true) {
            try {
                ratingChoice = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Enter a integer value");
                scanner.next();
            }
        }
        
        System.out.println("\nWould you like to write a comment\n yes \n no");
        String commentChoice = "";
            while (true) {
                commentChoice = scanner.next();
                if(commentChoice.equalsIgnoreCase("yes"))
                {
                    choice = true;
                    break;
                }
                else if(commentChoice.equalsIgnoreCase("no"))
                {
                    choice = false;
                    break;
                }
                System.out.println("Enter a valid choice (yes/no)");
            }
            if(choice)
            {
                System.out.println("Enter comment : ");
                scanner.nextLine();
                comment = scanner.nextLine();
            }
            else{
                comment="null";
            }
            try {
                addFeedback(ratingChoice , comment , currentOrderId);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    private static void addFeedback(int rating , String comment , int orderid) throws SQLException 
    {
        String sqlInsert = "insert into feedback(orderid , User_id , rating , comments , feedback_timeStamp) values(?,?,?,?,CURRENT_TIMESTAMP)";
        PreparedStatement pst = con.prepareStatement(sqlInsert);
        pst.setInt(1, orderid);
        pst.setInt(2, foodzee_login.user_id);
        pst.setInt(3, rating);
        pst.setString(4, comment);
        int i = pst.executeUpdate();
        if(i>0)
        {
            System.out.println("Thank You for your feedback");
        }
    }
}
