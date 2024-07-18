package Payment;

import java.util.Scanner;

public class cashPay {
    
    static Scanner sc = new Scanner(System.in);
    public static void cashDelivery(int orderId) throws Exception
    {
        System.out.println("Order has been placed based on cash on delivery");
        mainPaymentMenu.updateOrderTransactionMode("cash on delivery", "Success" , orderId);
    }
}
