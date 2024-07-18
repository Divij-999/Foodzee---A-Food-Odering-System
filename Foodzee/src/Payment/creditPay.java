package Payment;

public class creditPay {

    public static void creditDelivery(int orderId) throws Exception
    {
        //                Take input of credit detials 
        System.out.println("Order has been placed based on credit delivery");
        mainPaymentMenu.updateOrderTransactionMode("credit card delivery", "Success", orderId);
    }
}