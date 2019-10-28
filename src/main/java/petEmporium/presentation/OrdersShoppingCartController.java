package petEmporium.presentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;
import petEmporium.customexceptions.DeletePurchase;
import petEmporium.customexceptions.FriendlyServerErrorException;
import petEmporium.entities.PetOrders;
import petEmporium.entities.ShoppingCart;
import petEmporium.interfaces.IOrdersShoppingCartController;
import petEmporium.interfaces.IOrdersShoppingCartService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrdersShoppingCartController implements IOrdersShoppingCartController {

    @Autowired
    private IOrdersShoppingCartService oscServe;

    private static Logger logger = LogManager.getLogger();

    @Override
    @GetMapping(value = "/allSubOrders")
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
        try {
            return oscServe.getAllShoppingCarts();
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @GetMapping(value = "/getAllOrders")
    public ResponseEntity<List<PetOrders>> getAllOrders() {
        try {
            return oscServe.getAllOrders();
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }


    @Override
    @GetMapping(value = "/getSubOrder{shoppingCartId}")
    public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Integer shoppingCartId) {
        try {
            return oscServe.getShoppingCartById(shoppingCartId);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @GetMapping(value = "/getOrders{orderId}")
    public ResponseEntity<PetOrders> getOrderById(@PathVariable Integer orderId) {
        try {
            return oscServe.getOrderById(orderId);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @PostMapping(value = "/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody PetOrders petOrders) {
        try {
            return oscServe.createOrder(petOrders);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @PostMapping(value = "/createShoppingCart")
    public ResponseEntity<String> createShoppingCart(@RequestBody ShoppingCart shopcart) {
        try {
            return oscServe.createShoppingCart(shopcart);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @PutMapping(value = "/updateOrder{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Integer orderId, @RequestBody PetOrders petOrders) {
        try {
            return oscServe.updateOrder(orderId, petOrders);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @PutMapping(value = "/updateShopping{cartId}")
    public ResponseEntity<String> updateShoppingCart(@PathVariable Integer cartId, @RequestBody ShoppingCart cart) {
        try {
            return oscServe.updateShoppingCart(cartId, cart);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @DeleteMapping(value = "/deleteOrder")
    public void deleteOrder() {
        throw new DeletePurchase();
    }

    @Override
    @DeleteMapping(value = "/deleteSubOrder")
    public void deleteShoppingCart() {
        throw new DeletePurchase();
    }

    @Override
    @GetMapping(value = "/totalSalesByItemType")
    public ResponseEntity<String> findTotalSalesByItemType() {
        try {
            return new ResponseEntity<>(oscServe.findTotalSalesByItemType(), HttpStatus.OK);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @GetMapping(value = "/item{itemId}/startDate{startDateString}/endDate{endDateString}")
    public ResponseEntity<String> findPurchasesInDateRange(@PathVariable String startDateString, @PathVariable String endDateString, @PathVariable int itemId) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        try {
            Date startDate = formatter.parse(startDateString);
            Date endDate = formatter.parse(endDateString);

            return new ResponseEntity<>(oscServe.findPurchasesInDateRange(startDate, endDate, itemId), HttpStatus.OK);
        } catch (ParseException ex) {
            ex.fillInStackTrace();
        }
        return null;
    }

}
