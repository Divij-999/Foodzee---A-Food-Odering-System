package userMenu;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import Driver.jdbc_driver;
import Restaurant.restaurant;

public class Restaurant_owner {

    static Scanner sc = new Scanner(System.in);
    public static int productId ;
    // private static String catName,subCatName;
    // public static double productPrice;
    static Connection con = jdbc_driver.con;

    public static void setProductName()
    {
        System.out.print(" Enter Product Name : ");
        sc.nextLine();
        admin.productName = sc.nextLine();
    }
    public static String getProductName()
    {
        return admin.productName;
    }
    public static void setProductPrice()
    {
        System.out.print("\nEnter Product Price : ");

        admin.productPrice = 0.0;
        while (true) {
            try {
                    admin.productPrice = sc.nextDouble();
                    break;
                } catch (Exception e) {
                    System.out.println("Enter a double value");
                    sc.next();
                }
        }
    }
    public static double getProductPrice()
    {
        return admin.productPrice;
    }
    public static void mainRestOwner() throws Exception
    {
        System.out.println("\n\t\t\t\t\t\t\t\tWelcome "+restaurant.ownerName+"\n");
        int rFlag1=0;
        while(rFlag1 ==0)
        {
            // System.out.println("=>Select\n 1)Add product Details\n 2)Remove Product Details\n 3)Update Product Details\n 4)Veiw Amount Earned\n 5)Veiw Product Sold\n 6)Display Product\n 7)Exit");
            System.out.println("\t\t\t\t\t\t\t\t╔════════════════════════════╗");
            System.out.println("\t\t\t\t\t\t\t\t║   Restaurant Owner Menu    ║");
            System.out.println("\t\t\t\t\t\t\t\t╠════════════════════════════╣");
            System.out.println("\t\t\t\t\t\t\t\t║ 1. Add product Details     ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 2. Remove Product Details  ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 3. Update Product Details  ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 4. Veiw Amount Earned      ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 5. Veiw Product Sold       ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 6. Display Product         ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 7. Exit                    ║");
            System.out.println("\t\t\t\t\t\t\t\t╚════════════════════════════╝");
            int ownerChoice = 0;
            while (true) {
                try {
                ownerChoice = sc.nextInt();
                break;
                } catch (Exception e) {
                    System.out.println("Enter a integer value");
                    sc.next();
                }
            }
            switch(ownerChoice)
            {
                case 1 :
                {   
                    searchSubResult();
                    int d =0;
                    if(admin.printSubCategory("true"))
                    {
                        //set and get Category 
                            setProductName();
                            setProductPrice();
                            System.out.println("-->Select Food Type : ");
                            // String search = "false";
                            admin.printFoodType("false");
                            admin.foodTypeChoice = 0;
                            while (true) {
                                try {
                                admin.foodTypeChoice = sc.nextInt();
                                break;
                                } catch (Exception e) {
                                    System.out.println("Enter a integer value");
                                    sc.next();
                                }
                            }
                            // search = "true";
                            admin.printFoodType("true");

                        if(admin.printProduct("true"))
                        {
                            System.out.println("---> Duplicate Entry "+admin.getProductName()+" for Product");
                            d=1;
                            break;
                        }
                        else{
                            String sql_InsertProd = "insert into product(product_Name,sub_CategoryId , price , foodTypeId , restaurant_id) values(?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(sql_InsertProd);
                            pst.setString(1,getProductName() );
                            pst.setInt(2, admin.subCategory_id);
                            pst.setDouble(3, getProductPrice());
                            pst.setInt(4,admin.foodType_id); // error line null value is being recived
                            restaurant.getRestaurantDetails();
                            pst.setInt(5, restaurant.restaurantId);
                            pst.executeUpdate();
                            
                        }
                    }
                    else{
                        System.out.println("Sub Category Not found");
                    }
                    if(d==0)
                    {
                        System.out.println("\n\tFood Product -"+getProductName()+"- added successfully\n");
                    }
                    break;
                }
                case 2 :
                {
                    int n =0;
                    admin.setProductName();
                    //set and get category 
                    if(admin.printProduct("true"))
                    {
                        String sql_deleteProd ="delete from product where product_Name=?";    
                        PreparedStatement pst = con.prepareStatement(sql_deleteProd);
                        pst.setString(1, admin.productName);
                        pst.executeUpdate();
                    }
                    else{
                        System.out.println("---> Search result for Product Name "+getProductName()+" not found");
                        n=1;
                    }
                    if(n==0)
                    {
                        System.out.println("\n\tProduct -"+getProductName()+"- deleted successfully\n");
                    }
                    break;
                }
                case 3 :
                {
                    System.out.println("-->Select Product : ");
                    // String search = "false";
                    admin.printProduct("false");
                    admin.ProdChoice = 0;
                    while (true) {
                        try {
                            admin.ProdChoice = sc.nextInt();
                                break;
                            } catch (Exception e) {
                                System.out.println("Enter a integer value");
                                sc.next();
                            }
                    }
                    //  search = "true";
                    // admin.printProduct("true");
                    // System.out.println(admin.productName);
                    int n =0 ;
                    if(admin.printProduct("true"))
                    {
                        String sql_updateProd ="update product set product_Name = ? , Price = ? where product_id=?";    
                        PreparedStatement pst = con.prepareStatement(sql_updateProd);
                        System.out.println("\t\t\t\t\t\t\t\t ------------------------ ");
                        System.out.println("\t\t\t\t\t\t\t\t|      Select            |");
                        System.out.println("\t\t\t\t\t\t\t\t|════════════════════════|");
                        System.out.println("\t\t\t\t\t\t\t\t| 1. Product Name        |");
                        System.out.println("\t\t\t\t\t\t\t\t| 2. Product Price       |");
                        System.out.println("\t\t\t\t\t\t\t\t| 3. Exit                |");
                        System.out.println("\t\t\t\t\t\t\t\t ------------------------ ");
                        int updateChoice = 0;
                        while (true) {
                                try {
                                   System.out.print("\n\t\t\t\t\t\t       ");
                                    updateChoice = sc.nextInt();
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Enter a integer value");
                                    sc.next();
                                }
                        }
                        switch(updateChoice)
                        {
                            case 1 :
                            {
                                setProductName();
                                break;
                            }
                            case 2 :
                            {
                                setProductPrice();
                                break;
                            }
                            case 3 :
                            {
                                break;
                            }
                        }
                        try {
                        pst.setString(1, admin.productName);
                        pst.setDouble(2, admin.productPrice);
                        pst.setInt(3 , productId);
                        } catch (Exception e) {

                            System.out.println(e);
                        }
                        pst.executeUpdate();//error line same case imdiate exit
                    }
                    else{
                        System.out.println("---> Search result for Product Name "+getProductName()+" not found");
                        n=1;
                    }
                    if(n==0)
                    {
                        System.out.println("\n\tProduct -"+getProductName()+"- updated successfully\n");

                    }
                    break;
                }
                case 4 :  {
                    // veiw amount earned
                    double netEarning = 0.0;
                    String findEarning = "select od.* , ot.totalamt from order_details od join order_type ot on od.orderid = ot.orderid where restaurant_id = ? ";
                    PreparedStatement pst = con.prepareStatement(findEarning);
                    pst.setInt(1, restaurant.restaurantId);
                    ResultSet rst = pst.executeQuery();
                    while(rst.next())
                    {
                        netEarning = netEarning + rst.getDouble("totalamt");
                    }
                    System.out.println("Total Earning of restaurant : "+netEarning);
                    break;
                }
                case 5 : 
                {
                    //veiw product sold
                    HashSet<String> productSoldName = new HashSet<>();
                    String sql = "{call simple()}";
                CallableStatement cst = jdbc_driver.con.prepareCall(sql);
                ResultSet rs = cst.executeQuery();
                while(rs.next())
                {
                    if(restaurant.restaurantId == rs.getInt("restaurant_id"))
                    {
                        productSoldName.add(rs.getString("product_Name"));
                    }
                }

                for (String string : productSoldName) {
                    System.out.println("Product Name : "+string);
                }

                    break;
                }
                case 6 :
                {
                    // String search = "false";
                    admin.byRestaurantid = true;
                    admin.printProduct("false");
                    break;
                }
                case 7 :
                {
                    rFlag1 = 1;
                    break;
                }
                default :
                {
                    System.out.println("Enter a valid choice");
                }
            }

        }
    }

/* looping left for invalid choice */

    public static void searchSubResult() throws Exception //searchSubCategroy()
    {
                System.out.println("-->Select Food Category : ");
                // String search = "false";
                admin.printCategory("false");
                admin.catChoice = 0;
                while (true) {
                    try {
                            admin.catChoice = sc.nextInt();
                            break;
                        } catch (Exception e) {
                            System.out.println("Enter a integer value");
                            sc.next();
                        }
                }
                // search = "true";
                System.out.println("category choice "+admin.catChoice);
                admin.byCategory = true;
                admin.printCategory("true");
                System.out.println("Selected Category Name : "+admin.categoryName);
                System.out.println("-->Select Sub Food Category : ");
                // String searchS = "false";
                admin.printSubCategory("false");
                admin.subCatChoice = 0;
                while (true) {
                    try {
                            admin.subCatChoice = sc.nextInt();
                            break;
                        } catch (Exception e) {
                            System.out.println("Enter a integer value");
                            sc.next();
                        }
                }
                // searchS = "true";
                
                System.out.println("Sub category choice "+admin.subCatChoice);
                admin.bySubCategory = true;
                admin.printSubCategory("true");
                System.out.println("Selected SubCategory Name :  : "+admin.getSubCategoryName());
    }
}