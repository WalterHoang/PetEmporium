package petEmporium.interfaces;

import petEmporium.entities.PetOrders;
import petEmporium.entities.ShoppingCart;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IOrdersShoppingCartDAO {
    List<PetOrders> getAllOrders();

    List<ShoppingCart> getAllShoppingCarts();

    PetOrders getOrderById(Integer orderId);

    ShoppingCart getShoppingCartById(Integer orderId);

    PetOrders createOrder(PetOrders petOrders);

    ShoppingCart createShoppingCart(ShoppingCart cart);

    PetOrders updateOrder(Integer orderId, PetOrders petOrders);

    ShoppingCart updateShoppingCart(Integer cartId, ShoppingCart cart);

    String findTotalSalesByItemType();

    String findPurchasesInDateRange(Date startDate, Date endDate, int itemId);

    //no Deletes needed

}
