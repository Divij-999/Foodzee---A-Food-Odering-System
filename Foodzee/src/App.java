import Driver.jdbc_driver;
import Login.foodzee_login;
import userMenu.menu;

public class App {
    public static void main(String[] args) throws Exception {

        //establish Connection Between MYSQL Workbench and vs code using JDBC
        (new jdbc_driver()).mainConnection();
        
        foodzee_login l1 = new foodzee_login();
        l1.main();
        if(l1.getStatus().equals("active"))
        {
            System.out.println("Status is active");
            menu m = new menu();
            m.mainMenu();
        }
        else{
            System.out.println("Not active");
        }
        
    }
}
