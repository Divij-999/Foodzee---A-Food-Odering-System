package userMenu;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import Driver.jdbc_driver;
import Login.foodzee_login;
import Payment.mainPaymentMenu;
import Restaurant.restaurant;

public class SearchProduct {
    static Scanner sc = new Scanner(System.in);
    static HashMap<Order_key,Order_data> hs = new HashMap<>();
    //Count From how many restaurant order has been placed
    static HashMap<Integer,String> countRest = new HashMap<>();
    public static HashSet<Integer> orderIdCount = new HashSet<>();
    
    public static int orderId , quantity;
    public static Double totalAmount = 0.0 ;
    static Connection con = jdbc_driver.con;

    public static void mainSearch() throws Exception
    {

        int flagCs = 0;
        while(flagCs==0)
        {
            System.out.println("\t\t\t\t\t\t\t\t╔════════════════════════════════╗");
            System.out.println("\t\t\t\t\t\t\t\t║     Search Product Menu        ║");
            System.out.println("\t\t\t\t\t\t\t\t╠════════════════════════════════╣");
            System.out.println("\t\t\t\t\t\t\t\t║ 1. Search by Food Category     ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 2. Search by Sub Food Category ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 3. Search by Product Name      ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 4. PLace Order                 ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 5. Cancel Order                ║");
            System.out.println("\t\t\t\t\t\t\t\t║ 6. Exit                        ║");
            System.out.println("\t\t\t\t\t\t\t\t╚════════════════════════════════╝");
            int customerMChoice = 0;
            while (true) {
                try {
                        customerMChoice = sc.nextInt();
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a integer value");
                        sc.next();
                    }
            }
            switch(customerMChoice)
            {
                
                case 1 :
                {
                    Restaurant_owner.searchSubResult();
                    System.out.println("-->Select Product : ");
                    // String search = "false";
                    admin.printProduct("false");
                    admin.ProdChoice =0;
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
                    admin.bySubCategory = true;
                    admin.printProduct("true");
                    System.out.println("Selected Product Name :  : "+admin.productName);

                    admin.byCategory = false; admin.bySubCategory = false;
                    selectQuantity();
                    break;
                }
                case 2 :
                { 
                    System.out.println("-->Select Sub Food Category : ");
                    // String search = "false";
                    admin.printSubCategory("false");
                     admin.subCatChoice = 0 ;
                     while (true) {
                        try {
                                admin.subCatChoice = sc.nextInt();
                                break;
                            } catch (Exception e) {
                                System.out.println("Enter a integer value");
                                sc.next();
                            }
                    }
                    //  search = "true";
                    admin.printSubCategory("true");
                    System.out.println("Selected SubCategory Name :  : "+admin.getSubCategoryName());
                    //product
                    System.out.println("-->Select Product : ");
                    //  search = "false";
                    admin.bySubCategory = true;
                    admin.printProduct("false");
                    admin.ProdChoice =0;
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
                    admin.bySubCategory = true;
                    admin.printProduct("true");
                    System.out.println("Selected Product Name :  : "+admin.productName);
                     admin.bySubCategory = false;
                    selectQuantity();
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
                    // System.out.println("REstar : "+admin.ProdChoice);
                    //  search = "true";
                    admin.printProduct("true");
                    System.out.println("Selected Product Name :  : "+admin.productName);
                    selectQuantity();
                    break;
                }
                case 4 :
                {
                    placeOrder();
                    hs.clear();
                    orderIdCount.clear();
                    break;
                }
                case 5 :
                {
                    cancelOrder();
                    break;
                }
                case 6 :
                {
                    flagCs =1;
                    break;
                }
                default :
                {
                    System.out.println("Enter a valid choice");
                }
            }
        }
    }
    
    public static void selectQuantity()
    {
        System.out.println("Enter Quantity : ");
        quantity = 0;
        while (true) {
            try {
                    quantity = sc.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Enter a integer value");
                    sc.next();
                }
        }
        System.out.println("Product "+admin.productName+" added to cart");
        addToCart(quantity);
    }

    public static void addToCart(int quantity)
    {
        //Update quantity as new quantity,amount + old quantity,amount if present in hashMap
        int updatedQuantity = quantity;
        double updatedAmt = 0.0;
        int updateExists = 0;
        for(Map.Entry<Order_key,Order_data> ord : hs.entrySet())
        {
            Order_key ok1 = ord.getKey();
            Order_data od2 = ord.getValue();
            System.out.println("Product Id : "+ok1.productId+" , Rest Id : "+od2.restaurant_name+" , Prduct Name : "+od2.product_name+" , Quantity : "+od2.quantity+" , Price per quantity: "+od2.amt);

            if((Restaurant_owner.productId == ok1.productId)&&(restaurant.restaurantId == ok1.restaurantId))
            {
                updatedQuantity = updatedQuantity+od2.quantity;
                updateExists=1;
                break;
            }
        }
        System.out.println("Exists Yupdate : "+updateExists);
        updatedAmt = updatedQuantity * admin.productPrice;
        
        Order_data od = new Order_data(restaurant.restaurantName, updatedQuantity, admin.productPrice , updatedAmt , admin.productName);
        Order_key ok = new Order_key(Restaurant_owner.productId , restaurant.restaurantId);
        hs.put(ok,od);

        countRest.put(restaurant.restaurantId , restaurant.restaurantName);
        totalAmount = totalAmount + updatedAmt;
        System.out.println("Price : "+totalAmount);
        admin.productName = ""; admin.categoryName ="" ; admin.subCategoryName=""; restaurant.restaurantName="";updatedAmt =0.0 ;updatedQuantity=0;
        admin.productPrice =0.0;

        for(Map.Entry<Order_key,Order_data> mprint : hs.entrySet())
        {
                Order_key ok1 = mprint.getKey();
                Order_data od1 = mprint.getValue();
                System.out.println("Product Id : "+ok1.productId+" , Rest Id : "+od1.restaurant_name+" , Prduct Name : "+od1.product_name+" , Quantity : "+od1.quantity+" , Price per quantity: "+od1.amt);

        }
    }
    public static void placeOrder() throws Exception
    {   
        for (Map.Entry it : countRest.entrySet()) {
            System.out.println("KEY : "+it.getKey());
            System.out.println("VALUE : "+it.getValue());
        }

            for(Map.Entry<Order_key,Order_data> mEntry : hs.entrySet())
            {
                
                Order_key ok1 = mEntry.getKey();
                Order_data od1 = mEntry.getValue();

                setOrderDetails(ok1.restaurantId);
                getOrderDetails(ok1.restaurantId);

                System.out.println("\nPrduct Name : "+od1.product_name+" , Quantity : "+od1.quantity+" , Price per quantity: "+od1.amt+"\n");

                setOrderType(od1.product_name, od1.quantity, od1.amt,ok1.productId);

                System.out.println("Total bill : "+totalAmount);
                int placeOrderChoice = 0;
                while (true) {
                    try {
                            placeOrderChoice = sc.nextInt();
                            break;
                        } catch (Exception e) {
                            System.out.println("Enter a integer value");
                            sc.next();
                        }
                }
                if(placeOrderChoice == 1)
                {
                    //Select mode of payment cash or credit;
                    
                        mainPaymentMenu.orderPayTypeMenu(orderId);
                }
                else{
                    System.out.println("Order canceled");
                }
            }
    }

    public static void setOrderType(String productName , int quantity , double updatedAmt , int product_id) throws Exception
    {
        String setOT = "insert into order_type(orderid , product_id , quantity , totalamt ) values(?,?,?,?) ";
        PreparedStatement pst = con.prepareStatement(setOT);
        pst.setInt(1, orderId);
        pst.setInt(2, product_id);
        pst.setInt(3, quantity);
        pst.setDouble(4, updatedAmt);
        pst.executeUpdate();

    }

    public static void getOrderDetails(int currentRest) throws Exception{
        String sqlR = "select * from order_details where restaurant_id = ? and User_id = ? and orderTransactionMode = ? ";
       PreparedStatement pst = con.prepareStatement(sqlR);
       pst.setInt(1, currentRest);
       pst.setInt(2, foodzee_login.user_id);
       pst.setString(3, "order waiting to be placed");
       ResultSet rst = pst.executeQuery();
       while(rst.next())
       {
                orderId = rst.getInt("orderid");
                System.out.println("Order id : "+orderId);
                orderIdCount.add(orderId);
            
       }
    }
    public static void setOrderDetails(int currentRest) throws Exception
    {
        
        Date javaData = new Date() ;
        java.sql.Date time = new java.sql.Date(javaData.getTime());
        int orderRestExists =0;
        String sqlSearchOrder = "select * from order_details where orderdate = ? and order_status = ? and restaurant_id = ?";
        PreparedStatement psts = con.prepareStatement(sqlSearchOrder);
        psts.setDate(1,time);
        psts.setString(2,"added to cart");
        psts.setInt(3, currentRest);
        ResultSet rst = psts.executeQuery();
        while(rst.next())
        {
            orderRestExists = 1;
            orderId = rst.getInt("orderid");

        }
        if(orderRestExists != 1)
        {    
            String insert_order = "insert into order_details(User_id , order_status ,orderTransactionMode , restaurant_id , orderdate) values(?,?,?,?,CURRENT_TIMESTAMP)";
            PreparedStatement pstO = con.prepareStatement(insert_order);
            pstO.setInt(1, foodzee_login.user_id);
            pstO.setString(2, "added to cart");
            pstO.setString(3, "order waiting to be placed");
            pstO.setInt(4, currentRest);
            pstO.executeUpdate();
        }

    }
    public static void cancelOrder() {

        try {
            printOrders();
            String delete_order = "delete from order_details where orderid = ?";
            PreparedStatement pstC = con.prepareStatement(delete_order);
            pstC.setInt(1, orderId); //orderid pending done
            pstC.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Order not found");
        }

    }
    public static void printOrders() throws Exception{
        String print_order = "select * from order_details where User_id = ? ";
        PreparedStatement pstS = con.prepareStatement(print_order);
        pstS.setInt(1, foodzee_login.user_id);
        ResultSet rst = pstS.executeQuery();
        while(rst.next())
        {
            orderId = rst.getInt("orderId");
            restaurant.restaurantId = rst.getInt("restaurant_id");
        }
    }
}

    class Order_data{

      int quantity ;
      String product_name , restaurant_name ;
      double amt , updatedAmt;
     public Order_data(String restaurant_name , int quantity , double amt , double updatedAmt , String product_name )
    {
        this.restaurant_name = restaurant_name;
        this.product_name = product_name;
        this.quantity = quantity;
        this.amt = amt;
        this.updatedAmt = updatedAmt;
        System.out.println("REST NAME : "+restaurant_name);
        System.out.println("PRODUCT NAME : "+product_name);
        System.out.println("QUANTITY : "+quantity);
        System.out.println("AMT : "+amt);
        System.out.println("UPDATED AMT : "+updatedAmt);
    }
}
 class Order_key{
     int productId , restaurantId;
    public Order_key(int productId, int restaurantId) {
        this.productId = productId;
        this.restaurantId = restaurantId;
        System.out.println("PRODUCT ID : "+productId);
        System.out.println("RESTAURANT ID : "+restaurantId);
    }

}
