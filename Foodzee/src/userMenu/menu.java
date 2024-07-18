package userMenu;

import Login.foodzee_login;
import Restaurant.restaurant;

public class menu {
    
    public static void mainMenu()
    {
        switch(foodzee_login.user_type)
        {
            case "Admin" :
            {
                admin admin = new admin();
                try {
                    
                    admin.mainAdmin();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }
            case "Restaurant Owner" :
            {
                restaurant r = new restaurant();
                try {
                    r.mainRestaurant();
                    if(restaurant.restaurantExistsStatus)
                {
                    System.out.println("Restaurant exists");

                    if(r.approvedRestaurant())
                    {
                        Restaurant_owner restaurantOwner = new Restaurant_owner();
                        restaurantOwner.mainRestOwner();
                    }
                    else {
                        System.out.println("\n\tRestaurant status pending to be approved!!!\n");
                    }
                }
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println(e);
                }
                break;
            }
            case "Customer" :
            {
                customer customer = new customer();
                try {
                    customer.mainCustomer();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }
            default :
            {
                System.out.println("***User type not found***");
                break;
            }
        }
    }
}
