package orderHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import Driver.jdbc_driver;
import Login.foodzee_login;

public class menuOrderHistory {
    static Scanner sc = new Scanner(System.in);
    public static int orderId;
    static Connection con = jdbc_driver.con;
    public static void veiwOrderHistory() throws Exception
    {
        int flagVoh =  0 ;
        while(flagVoh==0)
        {
            // System.out.println("=>Select\n 1)Veiw all order \n 2)Search order by date\n 3)Exit");
            System.out.println("\t\t\t\t\t\t\t\t --------------------------");
            System.out.println("\t\t\t\t\t\t\t\t|      Order History       |");
            System.out.println("\t\t\t\t\t\t\t\t|══════════════════════════|");
            System.out.println("\t\t\t\t\t\t\t\t| 1. Veiw all order        |");
            System.out.println("\t\t\t\t\t\t\t\t| 2. Search order by date  |");
            System.out.println("\t\t\t\t\t\t\t\t| 3. Exit                  |");
            System.out.println("\t\t\t\t\t\t\t\t --------------------------");
            int orderHisChoice = 0;
            while (true) {
                try {
                    orderHisChoice = sc.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Enter a integer value");
                }
            }
            switch(orderHisChoice)
            {
                case 1 :
                {
                    String sql_searchHis = "select * from order_details";
                    PreparedStatement pst = con.prepareStatement(sql_searchHis);
                    // pst.setInt(1, login.user_id);
                    ResultSet rst = pst.executeQuery();
                    while(rst.next())
                    {
                        if(foodzee_login.user_id == rst.getInt("User_id"))
                        {     
                            System.out.println("->Order on date : "+rst.getDate("orderdate"));
                            orderId = rst.getInt("orderid");
                            printAllOrder.printAllOrderDetails(orderId);
                            break;
                        }
                    }
                    break;
                }
                case 2 :
                {
                    
                    System.out.println("-> Select Date You want to search");
                    String sql_searchDat = "select * from order_details";
                    PreparedStatement pst = con.prepareStatement(sql_searchDat);
                    ResultSet rst = pst.executeQuery();
                    while(rst.next())
                    {
                        if(foodzee_login.user_id == rst.getInt("User_id"))
                        {
                            System.out.println("    "+rst.getInt("orderid")+") "+rst.getString("orderdate"));

                        }
                    }
                    int dateChoice = sc.nextInt();
                    printAllOrder.printAllOrderDetails(dateChoice);
                    
                    break;
                }
                case 3 :
                {
                    flagVoh = 1;
                    break;
                }
                default :
                {
                    System.out.println("\tEnter a valid choice");
                    break;
                }
            }
        }
    } 
}
