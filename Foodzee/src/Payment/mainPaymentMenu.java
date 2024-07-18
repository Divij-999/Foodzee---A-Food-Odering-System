package Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import Driver.jdbc_driver;
import Feedback.feedback;

public class mainPaymentMenu {
    static Scanner sc = new Scanner(System.in);

    public static void orderPayTypeMenu(int orderId) throws  Exception 
    {
        int orderFlag = 0;
        while(orderFlag == 0)
        {
            System.out.println("\t\t\t\t\t\t\t\t╔══════════════════════════╗");
            System.out.println("\t\t\t\t\t\t\t\t║      Mode of Payment     ║");
            System.out.println("\t\t\t\t\t\t\t\t╠══════════════════════════╣");
            System.out.println("\t\t\t\t\t\t\t\t║ 1. Cash on delivery      ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 2. Order by credit card  ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 3. Exit                  ║");
            System.out.println("\t\t\t\t\t\t\t\t╚══════════════════════════╝");
            int payChoice = 0;
            while (true) {
                try {
                    payChoice = sc.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Enter a integer value");
                }
            }
            switch(payChoice)
            {
                case 1 :
                {
                    cashPay.cashDelivery(orderId);
                    //feedback
                    orderFlag =1;
                    break;
                }
                case 2 :
                {
                    creditPay.creditDelivery(orderId);
                    //feedback
                    orderFlag =1;
                    break;
                }
                case 3 :
                {
                    System.out.println("Payment Canceled");
                    updateOrderTransactionMode("null", "Canceled" , orderId);
                    orderFlag =1 ;
                    break;
                }
                default :
                {
                    System.out.println("Select a valid choice");
                    break;
                }
            }
        }
        feedback.mainFeedback();
        
    }

    public static void updateOrderTransactionMode(String transactionMode , String orderStatus , int orderId) throws Exception{

        String searchTransactionMode = "select * from order_details";
        PreparedStatement pst = jdbc_driver.con.prepareStatement(searchTransactionMode);
        // pst.setInt(1,orderId);
        ResultSet rs = pst.executeQuery();
        while(rs.next())
        {
            String approvel  = "update order_details set orderTransactionMode = ? , order_status = ? where orderid = ? ";
            PreparedStatement pst1 = jdbc_driver.con.prepareStatement(approvel);
            pst1.setString(1, transactionMode);
            pst1.setString(2, orderStatus);
            pst1.setInt(3,orderId);
            pst1.executeUpdate();
        }
    }
}
