package petEmporium.interfaces;

import org.springframework.http.ResponseEntity;
import petEmporium.entities.PetOrders;
import petEmporium.entities.ShoppingCart;

import java.util.Date;
import java.util.List;

public interface IOrdersShoppingCartService {
    ResponseEntity<List<ShoppingCart>> getAllShoppingCarts();

    ResponseEntity<List<PetOrders>> getAllOrders();

    ResponseEntity<PetOrders> getOrderById(Integer orderId);

    ResponseEntity<ShoppingCart> getShoppingCartById(Integer shoppingCartId);


    ResponseEntity<String> createOrder(PetOrders petOrders);

    ResponseEntity<String> createShoppingCart(ShoppingCart cart);

    ResponseEntity<String> updateOrder(Integer orderId, PetOrders petOrders);

    ResponseEntity<String> updateShoppingCart(Integer cartId, ShoppingCart cart);

    String findTotalSalesByItemType();

    String findPurchasesInDateRange(Date startDate, Date endDate, int itemId);


}
