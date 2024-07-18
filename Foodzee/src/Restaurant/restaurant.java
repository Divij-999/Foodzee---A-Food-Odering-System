package Restaurant;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Driver.jdbc_driver;
import Login.foodzee_login;
import userMenu.admin;

public class restaurant {
    
    static Scanner sc = new Scanner(System.in);
    static Connection con = jdbc_driver.con;
    private static String  ownerEmail  ;
    public static String restaurantName="" ,restaurantAddress="" , ownerName="" , uniqueId ,restaurantType , restaurantApprovalStatus ="Waiting", restaurantPhone ;
    public static boolean restaurantExistsStatus;
    public static int restaurantId;
    

    public static void setRestaurantDetails()
    {
        System.out.println("Enter Restaurant Name : ");
        restaurantName = sc.nextLine();

        ownerName = foodzee_login.getName();
        ownerEmail = foodzee_login.getEmail();

        System.out.println("Enter Restaurant Mobile number : ");
        foodzee_login.setPhoneNo();
        restaurantPhone = foodzee_login.getPhoneNo();

        restaurantAddress = foodzee_login.getAddress();

        System.out.println("Enter Restaurant Type : ");
        restaurantType =sc.nextLine();   
    }
   
    public static String getRestaurantName()
    {
        return restaurantName;
    }
    
    public static String getOwnerName()
    {
        return ownerName;
    }
    public static String getOwnerEmail()
    {
        return ownerEmail;
    }
    public static String getRestaurantType()
    {
        return restaurantType;
    }
    public static String getRestaurantPhoneNo()
    {
        return restaurantPhone;
    }
    public static String getRestaurantAddress()
    {
        return restaurantAddress;
    }
    public static void setRestaurantApprovelStatus() throws Exception
    {
        admin.restaurantApprovalList(getRestaurantName());
        System.out.println("Request Sent ");
    }
    public static String getRestaurantApprovelStatus(String requestStatus) throws Exception
    {
        String serachRes  = "select * from restaurant_details";
        PreparedStatement pst = con.prepareStatement(serachRes);
        ResultSet rs = pst.executeQuery();
        while(rs.next())
        {
                System.out.println("Restaurnat : "+restaurantName);
                String approvel  = "update restaurant_details set Restaurant_approvelStatus = ? where restaurant_id = ?";
                PreparedStatement pst1 = con.prepareStatement(approvel);
                pst1.setString(1, requestStatus);
                pst1.setInt(2,restaurantId);
                pst1.execute();
        }
        restaurantApprovalStatus = requestStatus;
        return restaurantApprovalStatus;
    }

    public static void mainRestaurant() throws Exception
    {
        //Insert data into table
        String sql_InsertRest = "insert into restaurant_details(User_id,Owner_name,Owner_Email,Restaurant_name,Restaurant_address,Restaurant_mobileno,Restaurant_type , Restaurant_approvelStatus) values(?,?,?,?,?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql_InsertRest);
        //check if restaurant exists
        if(checkRestaurantExists()){ 
                getRestaurantDetails();  
                System.out.println("Restaurant name : "+restaurantName);
                restaurantExistsStatus = true;
        } 
        else
        {
            System.out.println("Enter your restaurant details");
                    
                setRestaurantDetails();               

                pst.setInt(1, foodzee_login.user_id);
                pst.setString(2, getOwnerName());
                pst.setString(3, getOwnerEmail());
                pst.setString(4, getRestaurantName());
                pst.setString(5, getRestaurantAddress());
                pst.setString(6, getRestaurantPhoneNo());
                pst.setString(7, getRestaurantType());
                pst.setString(8, restaurantApprovalStatus);
                
                int i=0;                    
                try {
                        i = pst.executeUpdate();
                        System.out.println("\n\tRestaurant added successfully\t");
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println(e);
                    System.out.println("Restaurant Name "+restaurantName+" already exists");
            }
            try {
                
                getRestaurantDetails();
                setRestaurantApprovelStatus();
            } catch (Exception e) {

                System.out.println(e);
            }
        }
         
    }
    public static boolean checkRestaurantExists()
    {
        try {
            String checkRestaurantExistance = "select * from restaurant_details";
            PreparedStatement pstR = con.prepareStatement(checkRestaurantExistance);
            ResultSet rst = pstR.executeQuery();
        while(rst.next())
        {                
                if(foodzee_login.getEmail().equals(rst.getString("Owner_Email")))
                {
                    // restaurantExistsStatus = true;
                    
                    return true;
                }
        }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        System.out.println("false");
        return false;

    }
    
    public static void getRestaurantDetails() throws Exception{ 
       
       String sqlR = "select * from restaurant_details";
       PreparedStatement pst = con.prepareStatement(sqlR);
       ResultSet rst = pst.executeQuery();
       while(rst.next())
       {
            if(foodzee_login.getEmail().equals(rst.getString("Owner_Email"))||(restaurantName.equals(rst.getString("Restaurant_name"))))
            {
            ownerName = rst.getString("Owner_name");
            ownerEmail = rst.getString("Owner_Email");
            restaurantAddress = rst.getString("Restaurant_address");
            restaurantName = rst.getString("Restaurant_name");
            restaurantType = rst.getString("Restaurant_type");
            restaurantId = rst.getInt("Restaurant_id");
            restaurantApprovalStatus = rst.getString("Restaurant_approvelStatus");
            restaurantPhone = rst.getString("Restaurant_mobileno");
            System.out.println("Res id : "+restaurantId);
             break;
            }
            
       }
    }

    public static boolean approvedRestaurant()
    {
        if(restaurantApprovalStatus.equals("Verified"))
        {
            return true;
        }
        return false;
    }
}