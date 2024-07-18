package userMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import Driver.jdbc_driver;
import Login.foodzee_login;
import orderHistory.menuOrderHistory;

public class customer {
    static Scanner scanner = new Scanner(System.in);

    public static void mainCustomer() throws Exception
    {
        // System.out.println("\n                                    Welcome "+foodzee_login.userName+"\n");
        System.out.println("\n\t\t\t\t\t\t\t\tWelcome "+foodzee_login.userName+"\n");

        Connection con = jdbc_driver.con ;
        int flagC = 0;
        while(flagC==0)
        {
            // System.out.println("=>Select\n 1)Search Product\n 2)Veiw Order history\n 3)Exit");
            System.out.println("\t\t\t\t\t\t\t\t╔════════════════════════╗");
            System.out.println("\t\t\t\t\t\t\t\t║      Customer Menu     ║");
            System.out.println("\t\t\t\t\t\t\t\t╠════════════════════════╣");
            System.out.println("\t\t\t\t\t\t\t\t║ 1. Search Product      ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 2. Veiw Order history  ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 3. Exit                ║");
            System.out.println("\t\t\t\t\t\t\t\t╚════════════════════════╝");

            int customerMChoice = scanner.nextInt();
            switch(customerMChoice)
            {
                case 1 :
                {
                    SearchProduct sp = new SearchProduct();
                    sp.mainSearch();
                    break;
                }
                case 2 :
                {
                    menuOrderHistory.veiwOrderHistory();
                    break;
                }
                case 3 :
                {
                    System.out.println("See you soon!!!");
                    System.exit(0);
                }
            }
        }
    }
}
