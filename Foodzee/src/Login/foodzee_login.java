package Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import Driver.jdbc_driver;

public class foodzee_login {

    
    private static String  userPassword ,userMobileNo ,userEmail,userAddress;
    private static String status="";
    public static int user_id ;
    public static String userName , user_type;
    public static boolean loginExistance = false;

    static Scanner scanner = new Scanner(System.in);
    static Connection con = jdbc_driver.con;
        
    public static void main() throws Exception {

        //Insert data into table
        String sql_Insert = "insert into login_details(User_fullName , User_password , User_email ,User_mobileNo , User_address , User_type , login_timeStamp) values(?,?,?,?,?,?,CURRENT_TIMESTAMP)";
        PreparedStatement pst = con.prepareStatement(sql_Insert);
        //inisating login
        int flag1=0;
        while (flag1==0) {

            // System.out.println("Select \n1)Login using Email \n2)Create new account\n3)Exit");
            System.out.println("\n\t\t\t\t\t\t       Welcome to Foodzee - A Food Ordering System\n");
            System.out.println("\t\t\t\t\t\t\t\t╔════════════════════════╗");
            System.out.println("\t\t\t\t\t\t\t\t║      Main Menu         ║");
            System.out.println("\t\t\t\t\t\t\t\t╠════════════════════════╣");
            System.out.println("\t\t\t\t\t\t\t\t║ 1. Login               ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 2. Register            ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 3. Exit                ║");
            System.out.println("\t\t\t\t\t\t\t\t╚════════════════════════╝");
            System.out.print("\n\t\t\t\t\t\t       Select");
            int login_input = 0;
            while (true) {
                try {
               System.out.print("\n\t\t\t\t\t\t     ");
                login_input = scanner.nextInt();
                break;
                } catch (Exception e) {
                    System.out.println("Enter a integer value");
                    scanner.next();
                }
            }

            switch(login_input)
            {
                case 1:
                {
                    int flag2=0;
                      while (flag2==0) {
                        setEmail();
                        setPassword();
                        
                        searchLogin();
                        if(status.equals("active"))
                        {
                            System.out.println("\n\t\t\t\t\t\t\t  *********Login Successfull*********");
                            // System.out.println("\n*********Login Successfull*********");
                            flag1 =1; flag2=1;
                            break;
                        }
                        else{
                            System.out.println("Enter valid login details");
                            System.out.println("Enter 1 to Continue Login else for login menu");
                            int re_choice = 0;
                               try {
                                    re_choice = scanner.nextInt();
                                } catch (Exception e) {
                                    System.out.println("Enter a integer value");
                                }
                            if(re_choice == 1)
                            {
                                System.out.println("Re loging procedure");
                                // flag2 = 0;
                            }
                            else{
                                //System.out.println("Creating account process");
                                System.out.println("\n");
                                flag2 =1;
                                break;
                            }
                        }
                    }
                    break;
                } 
                case 2 :
                {
                    System.out.println("\n\t\t\t\t\t\t     Enter your details");
                    // System.out.println("Enter your details");
                                System.out.print("\n\t\t\t\t\t\t       Select");

                    
                    setUserDetails();
                    System.out.print("\n\t\t\t\t\t\t     Enter Mobile number :");
                    setPhoneNo();

                    //add data to database
                    pst.setString(1, getName());
                    pst.setString(2, getPassword());
                    pst.setString(3, getEmail());
                    pst.setString(4, getPhoneNo());
                    pst.setString(5, getAddress());
                    pst.setString(6, getUserType());

                    int i = pst.executeUpdate();
                    if(i>0)
                    {
                        System.out.println("Insertion success");
                    }
                    else{
                        System.out.println("Insertion Failed");
                    }
                    System.out.println("\n\t\t\t\t\t\t     Account Created Successfully");

                    searchLogin();
                    flag1 = 1;
                    break;
                }
                case 3 :
                {
                    System.exit(0);
                    break;
                }
                default :
                {
                    System.out.println("\n\t\t\t\t\t\t     Select from the given options to procedd");
                    break;
                }
            }
        }
    }

    //set and get login data
    public static void setUserDetails()
    {
        System.out.print("\n\t\t\t\t\t\t     Enter First Name : ");
        System.out.print("\n\t\t\t\t\t\t     ");
        String userFirstName = scanner.next(); 
        System.out.print("\n\t\t\t\t\t\t     Enter Middle name : ");
        System.out.print("\n\t\t\t\t\t\t     ");
        String userMiddleName = scanner.next();
        System.out.print("\n\t\t\t\t\t\t     Enter Last name : ");
        System.out.print("\n\t\t\t\t\t\t     ");
        String userLastName = scanner.next();
        userName = userFirstName +" "+ userMiddleName +" "+ userLastName ;

        setEmail();
        setPassword();

        int uflag =0;
        while(uflag==0)
        {
            // System.out.println("--->Select User Type \n   1)Restaurant Owner\n   2)Customer");
            System.out.println("\t\t\t\t\t\t\t\t╔════════════════════════╗");
            System.out.println("\t\t\t\t\t\t\t\t║    Select User Type    ║");
            System.out.println("\t\t\t\t\t\t\t\t╠════════════════════════╣");
            System.out.println("\t\t\t\t\t\t\t\t║ 1. Restaurant Owner    ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 2. Customer            ║");
            System.out.println("\t\t\t\t\t\t\t\t╚════════════════════════╝");
            int us =0;
            while (true) {
                try {
                System.out.print("\n\t\t\t\t\t\t     ");
                us = scanner.nextInt();
                break;
                } catch (Exception e) {
                    System.out.println("Enter a integer value");
                    scanner.next();
                }
            }
            switch(us)
            {
                case 1 :
                {
                    user_type = "Restaurant Owner";
                    uflag=1;
                    break;
                }
                case 2 :
                {
                    user_type = "Customer";
                    uflag =1;
                    break;
                }
                default :
                {
                    System.out.println("Enter a valid choice");
                    uflag = 0;
                    break;
                }
            }
        }

        System.out.print("\n\t\t\t\t\t\t     Enter Address : ");
        scanner.nextLine();
        System.out.print("\n\t\t\t\t\t\t     ");
        userAddress = scanner.nextLine();

    }
    public static String getName()
    {
        return userName;
    }
    public static void setPhoneNo()
    {       
        while (true) {
               System.out.print("\n\t\t\t\t\t\t     ");
                userMobileNo = scanner.next();
                if(userMobileNo.length()==10)
                    {
                        break;
                    }
                System.out.println("\n\t\t\t\t\t\t     Enter a valid 10 digit no.");
            }
    }
    public static String getPhoneNo()
    {
        return userMobileNo;
    }
    public static void setEmail()
    {
        System.out.print("\n\t\t\t\t\t\t     Enter E_mail id : ");
        System.out.print("\n\t\t\t\t\t\t     ");
        userEmail = scanner.next();
    }
    public static String getEmail()
    {
        return userEmail;
    }
    public static void setPassword()
    {
        System.out.print("\n\t\t\t\t\t\t     Enter password : ");
        System.out.print("\n\t\t\t\t\t\t     ");
        userPassword = scanner.next();
    }
    public static String getPassword()
    {
        return userPassword;
    }
    
    public static String getAddress()
    {
        return userAddress;
    }
    public static String getStatus()
    {
        return status;
    }
    public static String getUserType()
    {
        return user_type;
    }
    //search login status 
    public static void searchLogin() throws Exception
    {
        String sqlO = "select * from login_details";
        PreparedStatement pst = con.prepareStatement(sqlO);
        ResultSet rs = pst.executeQuery();
        while(rs.next())
        {
            if(getEmail().equals(rs.getString("User_email"))&&getPassword().equals(rs.getString(("User_password"))))
            {
                loginExistance = true;
                
                user_id= rs.getInt("User_id");
                user_type = rs.getString("User_type");
                userName = rs.getString("User_fullName");
                status = "active";
                userAddress = rs.getString("User_address");
            }
        }
    }
}
