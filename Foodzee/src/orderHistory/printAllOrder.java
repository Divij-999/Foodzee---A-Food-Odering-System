package orderHistory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Driver.jdbc_driver;
import Login.foodzee_login;

public class printAllOrder {
    
    public static int ordertid ;
    public static void printAllOrderDetails(int orderId) throws Exception
    {
        
        boolean foundOrder = false;
        
        String sql_Order = "select od.* , ot.ordertid from order_details od join order_type ot on od.orderid = ot.orderid where User_id = ? ";
        PreparedStatement pst = jdbc_driver.con.prepareStatement(sql_Order);
        pst.setInt(1, foodzee_login.user_id);
        ResultSet rst = pst.executeQuery();
        if(rst != null)
        {
            while(rst.next())
            {
                ordertid = rst.getInt("ordertid");
                String sql = "{call simple()}";
                CallableStatement cst = jdbc_driver.con.prepareCall(sql);
                ResultSet rs = cst.executeQuery();
                while(rs.next())
                {
                    if(ordertid == rs.getInt("ordertid"))
                    {
                        System.out.println("   Order Transaction id : "+ordertid+" , Restaurant Name : "+rs.getString("Restaurant_name")+" , Product Name : "+rs.getString("product_Name")+" , Quantity : "+rs.getInt("quantity")+" , Total amount : "+rs.getDouble("totalamt"));
                        foundOrder = true;
                        break;
                    }
                }
            }
        }
        else{
            
        System.out.println(" ");
        System.out.println(foundOrder);
            if(!foundOrder)
            {
                System.out.println("\n\tNo order placed or order was canceled\n");
            }
        }
        
    }
}
