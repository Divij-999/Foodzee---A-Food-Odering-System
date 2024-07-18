package userMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Driver.jdbc_driver;
import Login.foodzee_login;
// import Restaurant.restaurant;
import Restaurant.restaurant;

public class admin {

    static Scanner scanner = new Scanner(System.in);
    static Connection con = jdbc_driver.con;
    public static String categoryName="",subCategoryName="",productName="", foodType="" ;
    public static Double productPrice=0.0;
    public static int category_id ,subCategory_id , foodType_id;
    public static int catChoice , subCatChoice , foodTypeChoice , ProdChoice , ProductCount = 0;
    public static boolean byCategory = false , bySubCategory = false , byRestaurantid = false;
    // private static String catName,subCatName;

    public static void mainAdmin() throws Exception {

        System.out.println("\n\t\t\t\t\t\t\t\t\tWelcome "+foodzee_login.userName+"\n");
        int flag3=0;
        //getRestaurantData();

        while (flag3==0) {
            System.out.println("\t\t\t\t\t\t\t\t╔═════════════════════════════════╗");
            System.out.println("\t\t\t\t\t\t\t\t║            Admin Menu           ║");
            System.out.println("\t\t\t\t\t\t\t\t╠═════════════════════════════════╣");
            System.out.println("\t\t\t\t\t\t\t\t║ 1.  Restaurant Approval         ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 2.  Add Food Category           ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 3.  Add Sub Food Category       ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 4.  Add foodType                ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 5.  Remove foodType             ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 6.  Remove Product              ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 7.  Remove Food Category        ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 8.  Remove Sub Food Category    ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 9.  Veiw Restaurant             ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 10. Exit                        ║");
            System.out.println("\t\t\t\t\t\t\t\t╚═════════════════════════════════╝");
            System.out.print("\n\t\t\t\t\t\t       Select : ");
            int adminChoice = 0;
            while (true) {
                try {
                adminChoice = scanner.nextInt();
                break;
                } catch (Exception e) {
                    System.out.println("Enter a integer value");
                    scanner.next();
                }
            }

            switch(adminChoice)
            {
                case 1 :
                {
                    getPendingRestaurantList();
                    break;
                }
                case 2 :
                {
                    int d =0;
                    //set and get Category
                    setCategoryName(); //insert category name from admin
                    //if the category exists in the menu then break and print duplicate value else add in db
                    if(printCategory("true"))
                    {
                        System.out.println("---> Duplicate Entry "+getCategoryName()+" for Food Category");
                        d=1;
                        break;
                    }
                    else{
                        String sql_InsertCat = "insert into food_category(category_name) values(?)";
                        PreparedStatement pst = con.prepareStatement(sql_InsertCat);
                        pst.setString(1, categoryName);
                        pst.executeUpdate();
                    }
                    if(d==0)
                    {
                        System.out.println("\n\tFood Category -"+getCategoryName()+"- added successfully\n");
                    }
                    break;
                }
                case 3 :
                {
                    int d =0;
                    System.out.println("-->Select Food Category : ");
                    //print category
                    printCategory("false");
                     catChoice = 0;
                     while (true) {
                        try {
                            catChoice = scanner.nextInt();
                            break;
                        } catch (Exception e) {
                            System.out.println("Enter a integer value");
                            scanner.next();
                        }
                    }
                    //select category
                    printCategory("true");
                    System.out.println(categoryName);
                    if(printCategory("true"))
                    {
                        setSubCategoryName();
                        if(printSubCategory("true"))        
                        {
                            System.out.println("---> Duplicate Entry "+getSubCategoryName()+" for Sub Food Category");
                            d=1;
                            break;
                        }
                        else{
                        //set and get SubCategory 
                                String sql_InsertSubCat = "insert into sub_foodcategory(category_id,sub_CategoryName) values(?,?)";
                                PreparedStatement pst = con.prepareStatement(sql_InsertSubCat);
                                pst.setInt(1,category_id );
                                pst.setString(2, subCategoryName);
                                pst.executeUpdate();
                        }
                    }
                    else{
                        System.out.println("Category Not found");
                    }
                    if(d==0)
                    {
                        System.out.println("\n\tSub Food Category -"+getSubCategoryName()+"- added successfully");
                    }
                    break;
                }
                case 4 :
                {
                    addFoodType();
                    break;
                }
                case 5 :
                {
                    //remove foodType
                    int n =0;
                    printFoodType("false");
                    foodTypeChoice = 0;
                        while (true) 
                        {
                            try {
                                foodTypeChoice = scanner.nextInt();
                                break;
                            } catch (Exception e) {
                                System.out.println("Enter a integer value");
                                scanner.next();
                            }
                        }
                        if(printFoodType("true"))
                        {
                            String delFoodType = "delete from foodtype where foodTypeName = ? ";
                            PreparedStatement pst1 = con.prepareStatement(delFoodType);
                            pst1.setString(1, foodType);
                            pst1.executeUpdate();
                        }
                    else{
                        System.out.println("---> Search result for Food Type "+foodType+" not found");
                        n=1;
                    }
                    
                    if(n==0)
                    {
                        System.out.println("\n\tFood Type - "+foodType+"- deleted successfully");
                    }
                    // printFoodType("true");
                    
                    break;
                }
                case 6 :
                {
                    int n =0;
                    printProduct("flase");
                    setProductName(); 
                    //set and get product 
                    if(printProduct("true")){
                        String sql_deleteProduct ="delete from product where product_Name=?";    
                        PreparedStatement pst = con.prepareStatement(sql_deleteProduct);
                        pst.setString(1, productName);
                        pst.executeUpdate();
                    }
                    else{
                        System.out.println("---> Search result for Product Name "+getProductName()+" not found");
                        n=1;
                    }
                    if(n==0)
                    {
                        System.out.println("\n\tProduct -"+getProductName()+"- deleted successfully");
                    }
                    break;
                }
                case 7 :
                {
                    int n =0;
                    printCategory("false");
                    setCategoryName();
                    //set and get category 
                    if(printCategory("true"))
                    {
                        String sql_deleteCat ="delete from food_category where category_name=?";    
                        PreparedStatement pst = con.prepareStatement(sql_deleteCat);
                        pst.setString(1, categoryName);
                        pst.executeUpdate();
                    }
                    else{
                        System.out.println("---> Search result for category Name "+getCategoryName()+" not found");
                        n=1;
                    }
                    if(n==0)
                    {
                        System.out.println("\n\tFood Category -"+getCategoryName()+"- deleted successfully");

                    }
                    break;
                }
                case 8 :
                {
                    int n =0;
                    printSubCategory("false");
                    setSubCategoryName();
                    //set and get category 
                    if(printSubCategory("true"))
                    {
                        String sql_deleteSubCat ="delete from sub_foodcategory where sub_CategoryName=?";    
                        PreparedStatement pst = con.prepareStatement(sql_deleteSubCat);
                        pst.setString(1, subCategoryName);
                        pst.executeUpdate();
                    }
                    else{
                        System.out.println("---> Search result for Sub Category Name "+getSubCategoryName()+" not found");
                        n=1;
                    }
                    if(n==0)
                    {
                        System.out.println("\n\tSub Food Category -"+getSubCategoryName()+"- deleted successfully");

                    }
                    break;

                }
                case 9 :
                {
                    try {
                        String veiwRestaurant = "select * from restaurant_details";
                        PreparedStatement pst = con.prepareStatement(veiwRestaurant);
                        ResultSet rs = pst.executeQuery();
                        System.out.println("Available Restaurant's : ");
                        while(rs.next())
                        {
                            System.out.println(" "+rs.getInt("restaurant_id")+") "+rs.getString("Restaurant_name"));
                        }
                    } catch (Exception e) {
                        System.out.println("No Result Found");
                        // System.out.println(e);
                    }
                    break;
                }
                case 10 :
                {
                    flag3 =1;
                    break;
                }
                default :
                {
                    System.out.println("Select a valid choice!!!");
                    break;
                }
            }
        }
    }

    public static void setCategoryName()
    {
        System.out.println("Enter Category Name : ");
        scanner.nextLine();
        categoryName = scanner.nextLine();
    }
    public static String getCategoryName()
    {
        return categoryName;
    }
    public static void setSubCategoryName()
    {
        System.out.println("Enter Sub Category Name : ");
        scanner.nextLine();
        subCategoryName = scanner.nextLine();
    }
    public static String getSubCategoryName()
    {
        return subCategoryName;
    }
    public static void setProductName()
    {
        System.out.println("Enter Product Name : ");
        // scanner.nextLine();
        productName = scanner.nextLine();
    }
    public static String getProductName()
    {
        return productName;
    }
    public static boolean printCategory(String search) throws Exception
    {
        int ex =0;
        String sql_search = "select * from food_category order by category_id asc";
        PreparedStatement pst = con.prepareStatement(sql_search);
        ResultSet rst = pst.executeQuery();
        // System.out.println("Search started");
        while(rst.next())
        {
            if(search.equals("true"))
            {
                if(catChoice == rst.getInt("category_id")||(getCategoryName().equals(rst.getString("category_name"))))
                {
                    categoryName = rst.getString("category_name");
                    category_id = rst.getInt("category_id");
                    ex=1;
                    break;
                }
            }
            if(search.equals("false")){
                System.out.println(" "+rst.getInt("category_id")+") "+rst.getString("category_name"));
            }
        }
        if (ex==1) {
                return true;
            } else {
                return false;
            }
    }
    public static boolean printSubCategory(String search ) throws Exception
    {
        int ex =0;
        String sql_search = "select * from sub_foodcategory order by sub_categoryId asc";
        if (byCategory) {
            sql_search = "select * from sub_foodcategory where category_id = "+catChoice+" order by sub_categoryId asc";
        }
        PreparedStatement pst = con.prepareStatement(sql_search);
        ResultSet rst = pst.executeQuery();
        // System.out.println("Search started");
        while(rst.next())
        {
            //select choice from menue
            if(search.equals("true"))
            {
                if(subCatChoice == rst.getInt("sub_categoryId")||(subCategoryName.equals(rst.getString("sub_CategoryName"))))
                {
                    subCategoryName = rst.getString("sub_CategoryName");
                    category_id = rst.getInt("category_id");
                    subCategory_id = rst.getInt("sub_categoryId");
                    ex = 1;
                    break;
                }
            }
            //prints menue
            if(search.equals("false")){
                // int count ;
                try {
                    System.out.println(" "+rst.getInt("sub_categoryId")+") "+rst.getString("sub_CategoryName")+"\n   Count of Product in "+rst.getString("sub_CategoryName")+" : "+getProductCount(rst.getInt("sub_categoryId")));

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
        if (ex==1) {
                return true;
            } else {
                return false;
            }
    }
    

    public static int getProductCount(int subCatId) throws SQLException , Exception
    {
        String countProduct = "select * from product where sub_categoryId = ?";
        PreparedStatement pst = con.prepareStatement(countProduct);
        pst.setInt(1,subCatId);
        ResultSet rst = pst.executeQuery();
        int count =0 ;
        while(rst.next())
        {
            count = count + 1;
        }
        return count;

    }
    public static boolean printProduct(String search) throws Exception
    {
        int ex =0;
        String sql_search = "select p.* , r.Restaurant_name from product p join restaurant_details r on r.restaurant_id = p.restaurant_id where sub_categoryId order by Price desc ";
        if (bySubCategory) {
            System.out.println("Here By sub category : "+subCatChoice);
            sql_search = "select p.* , r.Restaurant_name from product p join restaurant_details r on r.restaurant_id = p.restaurant_id where sub_categoryId = "+subCatChoice+" order by Price desc";
        }
        if(byRestaurantid)
        {
            sql_search = "select p.* , r.Restaurant_name from product p join restaurant_details r on r.restaurant_id = p.restaurant_id where r.restaurant_id = "+restaurant.restaurantId+" order by Price desc";
        }
        PreparedStatement pst = con.prepareStatement(sql_search);
        ResultSet rst = pst.executeQuery();
        while(rst.next())
        {

            if(search.equals("true"))
            {
                if(ProdChoice == rst.getInt("product_id")||(productName.equals(rst.getString("product_Name"))))
                {
                    
                        productName = rst.getString("product_Name");
                        restaurant.restaurantId = rst.getInt("restaurant_id");
                        productPrice = rst.getDouble("Price");
                        subCategory_id = rst.getInt("sub_categoryId");
                        restaurant.restaurantName = rst.getString("Restaurant_name");
                        Restaurant_owner.productId = rst.getInt("product_id");
                        ex = 1;
                    break;
                    
                }
            }
            if(search.equals("false")){
                System.out.println(" "+rst.getInt("product_id")+") "+rst.getString("product_Name")+"          Price : "+rst.getDouble("Price"));
            }
        }
        if (ex==1) {
                return true;
            } else {
                return false;
            }
    }
    public static void restaurantApprovalList(String restaurantName) throws Exception
    {
        try {
            // pending to create pendingrestaurantrequest
        String sql_insertPending = "insert into pendingrestaurantrequest(restaurant_id , Restaurant_name , Restaurant_approvelStatus)  values(?,?,?)";
        PreparedStatement psPend = con.prepareStatement(sql_insertPending);
        
        System.out.println(restaurant.restaurantId);
        psPend.setInt(1, restaurant.restaurantId);
        psPend.setString(2, restaurantName);
        psPend.setString(3, restaurant.restaurantApprovalStatus);
        psPend.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public static void getPendingRestaurantList() throws Exception 
    {
        String searchList = "select * from pendingrestaurantrequest";
        PreparedStatement pst = con.prepareStatement(searchList);
        ResultSet rst = pst.executeQuery();
        if(rst != null)
        {
            System.out.println("\n\t No Restaurant in queue\n");
        }
        while(rst.next())
        {
            restaurant.restaurantId = rst.getInt("restaurant_id");
            restaurant.restaurantName = rst.getString("Restaurant_name");
            if(selectStatus())
            {
                String deleteFromList = "delete from pendingrestaurantrequest where restaurant_id = ?";
                PreparedStatement pstDelete = con.prepareStatement(deleteFromList);
                pstDelete.setInt(1, restaurant.restaurantId);
                pstDelete.executeUpdate();
            }
        }
    }
    //approvel status
    public static boolean selectStatus() throws Exception
    {
        System.out.println("Approvel Request from Restaurant Name : "+restaurant.restaurantName);
        restaurant.getRestaurantDetails();
                        int a =0 ;
                        while(a==0)
                        {
                            System.out.println("-->Select\n  1)Approve Request\n  2)Deny Request");
                            int request = 0;
                            while (true) {
                                try {
                                request = scanner.nextInt();
                                break;
                                } catch (Exception e) {
                                    System.out.println("Enter a integer value");
                                    scanner.next();
                                }
                            }
                            switch(request)
                            {
                                case 1 :
                                {
                                    System.out.println("Request Approved");
                                    restaurant.getRestaurantApprovelStatus("Verified");
                                    a=1;
                                    return true;
                                }
                                case 2 :
                                {
                                    System.out.println("Request Denied");
                                    restaurant.getRestaurantApprovelStatus("Denied");
                                    a=1;
                                    return true;
                                }
                                default :
                                {
                                    System.out.println("Select a valid choice ");
                                    break;
                                }
                            }
                        }
                    return false;
    }
    public static void setFoodType()
    {
        System.out.println("Enter Food Type : ");
        scanner.nextLine();
        foodType = scanner.nextLine();
    }
    public static String getFoodType()
    {
        return foodType;
    }
    public static void addFoodType() throws Exception
    {
        int d =0;
        setFoodType();
        if(printFoodType("true"))        
            {
                System.out.println("---> Duplicate Entry "+getFoodType()+" for Food Type");
                d=1;
            }
            else{
                    //set and get SubCategory 
                String insertFoodType = "insert into foodtype(foodTypeName) values(?)";
                PreparedStatement pst = con.prepareStatement(insertFoodType);
                pst.setString(1, getFoodType());
                pst.executeUpdate();
            }
        if(d==0)
        {
            System.out.println("\n\tFood Type -"+getFoodType()+"- added successfully\n");
        }
    }
    public static boolean printFoodType(String search) throws Exception{

        int ex =0;
        String printFoodType = "select * from foodtype order by foodTypeId asc";
        PreparedStatement pst = con.prepareStatement(printFoodType);
        ResultSet rst = pst.executeQuery();
        while(rst.next())
        {
            if(search.equals("true"))
            {
                if(foodTypeChoice == rst.getInt("foodTypeId") || foodType.equals(rst.getString("foodTypeName")))
                {
                    foodType = rst.getString("foodTypeName");
                    foodType_id = rst.getInt("foodTypeId");
                    ex =1;
                    break;
                }
            }
            if(search.equals("false")){
                System.out.println(" "+rst.getInt("foodTypeId")+") "+rst.getString("foodTypeName"));
            }
        }
        if (ex==1) {
                return true;
            } else {
                return false;
            }
    }
    
}
