package petEmporium.interfaces;

import org.springframework.http.ResponseEntity;
import petEmporium.entities.PetOrders;
import petEmporium.entities.ShoppingCart;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IOrdersShoppingCartController {
    ResponseEntity<List<ShoppingCart>> getAllShoppingCarts();

    ResponseEntity<List<PetOrders>> getAllOrders();

    ResponseEntity<PetOrders> getOrderById(Integer orderId);

    ResponseEntity<ShoppingCart> getShoppingCartById(Integer shoppingCartId);


    ResponseEntity<String> createOrder(PetOrders petOrders);

    ResponseEntity<String> createShoppingCart(ShoppingCart cart);

    ResponseEntity<String> updateOrder(Integer orderId, PetOrders petOrders);

    ResponseEntity<String> updateShoppingCart(Integer cartId, ShoppingCart cart);

    void deleteOrder();

    void deleteShoppingCart();


    ResponseEntity<String> findTotalSalesByItemType();

    ResponseEntity<String> findPurchasesInDateRange(String startDateString, String endDateString, int itemId);


}
