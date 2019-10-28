/**
 * This is the service layer, where all the logic, calculations and validation is performed.
 */
package petEmporium.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import petEmporium.customexceptions.NotFound;
import petEmporium.customexceptions.PurchaseInvalidInput;
import petEmporium.data.OrdersShoppingCartDAO;
import petEmporium.entities.Inventory;
import petEmporium.entities.Pet;
import petEmporium.entities.PetOrders;
import petEmporium.entities.ShoppingCart;
import petEmporium.interfaces.IInventoryDao;
import petEmporium.interfaces.IOrdersShoppingCartService;
import petEmporium.interfaces.IPetDao;
import petEmporium.interfaces.IPetUsersEmailAddressDAO;

import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.List;

@Service
public class OrdersShoppingCartService implements IOrdersShoppingCartService {
    @Autowired
    private OrdersShoppingCartDAO oscDAO;
    @Autowired
    private
    IInventoryDao iInvenDao;
    @Autowired
    private IPetDao petDao;
    @Autowired
    IPetUsersEmailAddressDAO pUEADAO;
    private ResponseEntity<String> strResEnt;
    private Double tax = 1.07;
    private static Logger logger = LogManager.getLogger();

    /**
     * gets all the shoppingCarts which are labelled as "subOrders" when typing them into JSON.
     *
     * @return List of ShoppingCarts, and a 200 HTTP Status.
     */
    @Override
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
        return new ResponseEntity<>(oscDAO.getAllShoppingCarts(), HttpStatus.OK);
    }

    /**
     * gets all the Orders
     *
     * @return List of Orders, and a 200 HTTP Status.
     */
    @Override
    public ResponseEntity<List<PetOrders>> getAllOrders() {
        return new ResponseEntity<>(oscDAO.getAllOrders(), HttpStatus.OK);
    }

    /**
     * gets an Order by ID
     *
     * @return An Order, and a 200 HTTP Status.
     */
    @Override
    public ResponseEntity<PetOrders> getOrderById(Integer orderId) {
        return new ResponseEntity<>(oscDAO.getOrderById(orderId), HttpStatus.OK);
    }

    /**
     * gets a ShoppingCart by ID
     *
     * @return An ShoppingCart, and a 200 HTTP Status.
     */
    @Override
    public ResponseEntity<ShoppingCart> getShoppingCartById(Integer shoppingCartId) {
        return new ResponseEntity<>(oscDAO.getShoppingCartById(shoppingCartId), HttpStatus.OK);
    }

    /**
     * Creates a shoppingCart by grabbing the JSON and parsing it.
     *
     * @param cart
     * @return The status of Created (204), if the criteria are met, otherwise it is a 400.
     */
    @Override
    public ResponseEntity<String> createShoppingCart(ShoppingCart cart) {
        try {
            if (cart.getPet() != null) { //If there is a pet id, set the line price as the price of the adoption fee
                Pet pet = petDao.getPetById((cart.getPet().getPetId()));
                cart.setLineTotal(petDao.getPetById(cart.getPet().getPetId()).getPetAdoptionFee());
                oscDAO.createShoppingCart(cart);
                strResEnt = new ResponseEntity<>(HttpStatus.CREATED);
                return strResEnt;
            } else if (cart.getItemOrder().getInventoryId() != null) {//If there is a item id, set the line price as the item price times the amount bought
                Double lineTotals =
                        iInvenDao.getInventoryItemById(cart.getItemOrder()
                                .getInventoryId()).getPrice();
                cart.setLineTotal(lineTotals * cart.getItemQuantity());
                oscDAO.createShoppingCart(cart);
                strResEnt = new ResponseEntity<>(HttpStatus.CREATED);
                return strResEnt;
            }
            strResEnt = new ResponseEntity<>(HttpStatus.BAD_REQUEST); //Returns a bad request error if neither condition is met.
            return strResEnt;
        } catch (Exception ex) { //outputs a custom exception.
            logger.debug(ex.getMessage());
            throw new PurchaseInvalidInput();
        }
    }

    /**
     * Creates an Order, auto-calculates the price, and also automatically changes the inventory amount if the purchase is sucessful.
     *
     * @param petOrders
     * @return 200, or if it is incorrect a Custom Exception
     */
    @Override
    public ResponseEntity<String> createOrder(PetOrders petOrders) {
        try {
            Double total = 0.00;
            Double linePrice = 0.00;
            String linePriceOutput = "";
            for (ShoppingCart cartie : petOrders.getSubOrders().stream().toArray(ShoppingCart[]::new)) {//filters through the order for the multiple shopping carts
                linePrice = oscDAO.getShoppingCartById(cartie.getCartId()).getLineTotal();
                linePriceOutput = "Line Order:" + linePrice.toString() + "\n" + linePriceOutput;
                total = linePrice + total;
                if (oscDAO.getShoppingCartById(cartie.getCartId()).getItemOrder() != null) {//if the shopping cart has an item, get the item from the inventory repo
                    Integer subtractAmount = oscDAO.getShoppingCartById(cartie.getCartId()).getItemQuantity();
                    Inventory t = oscDAO.getShoppingCartById(cartie.getCartId()).getItemOrder();
                    iInvenDao.getInventoryItemById(t.getInventoryId());
                    if (iInvenDao.getInventoryItemById(t.getInventoryId()).getNumberAvailable() > subtractAmount) {//if the inventory amount is greater than the desired purchase amount
                        iInvenDao.getInventoryItemById(t.getInventoryId()).setNumberAvailable(
                                iInvenDao.getInventoryItemById(
                                        oscDAO.getShoppingCartById(cartie.getCartId()).getItemOrder().getInventoryId()).getNumberAvailable()
                                        - subtractAmount);//Sets the new inventory amount
                    }
                } else if (oscDAO.getShoppingCartById(cartie.getCartId()).getPet() != null) {
                    Pet p = oscDAO.getShoppingCartById(cartie.getCartId()).getPet();
                    if (!petDao.getPetById(p.getPetId()).isBeenAdopted()) {
                        petDao.getPetById(p.getPetId()).setBeenAdopted(true);//sets the pet Adoption status to true.
                    } else {
                        throw new PurchaseInvalidInput();
                    }
                }
            }
            total = (Math.floor(100 * (total * tax))) / 100;//Makes sure the total is rounded to the nearest penny
            petOrders.setTotalPrice(total);//sets the total price
            oscDAO.createOrder(petOrders);
            strResEnt = new ResponseEntity<>("Line Prices:\n" + linePriceOutput + "\n" + "Purchase Amount Total: \n" + total.toString(), HttpStatus.CREATED);
            return strResEnt;
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new PurchaseInvalidInput();
        }
    }

    /**
     * Updates the order. Right now it acts similarly to a create,
     * so essentially you are just creating a new order, just setting the id the same.
     *
     * @param orderId
     * @param petOrders
     * @return 200
     */
    @Override
    public ResponseEntity<String> updateOrder(Integer orderId, PetOrders petOrders) {
        try {
            oscDAO.getOrderById(orderId).setTotalPrice(0.00);
            Double total = 0.00;
            Double linePrice;
            String linePriceOutput = "";
            tax = 1.07;
            if (oscDAO.getOrderById(orderId).getSubOrders().stream().toArray(ShoppingCart[]::new).length > 0) {
                for (ShoppingCart oldOrder : oscDAO.getOrderById(orderId).getSubOrders().stream().toArray(ShoppingCart[]::new)) {//filters through the order for the multiple shopping carts
                    if (oscDAO.getShoppingCartById(oldOrder.getCartId()).getItemOrder() != null) {//if the shopping cart has an item, get the item from the inventory repo
                        Integer addAmount = oscDAO.getShoppingCartById(oldOrder.getCartId()).getItemQuantity();
                        Inventory t = oscDAO.getShoppingCartById(oldOrder.getCartId()).getItemOrder();
                        iInvenDao.getInventoryItemById(t.getInventoryId());

                        iInvenDao.getInventoryItemById(t.getInventoryId()).setNumberAvailable(
                                iInvenDao.getInventoryItemById(
                                        oscDAO.getShoppingCartById(oldOrder.getCartId()).getItemOrder().getInventoryId()).getNumberAvailable()
                                        + addAmount);//Reverts to the old inventory amount

                    } else if (oscDAO.getShoppingCartById(oldOrder.getCartId()).getPet() != null) {//if the person wants to abandon the animal
                        Pet p = oscDAO.getShoppingCartById(oldOrder.getCartId()).getPet();
                        if (petDao.getPetById(p.getPetId()).isBeenAdopted()) {
                            petDao.getPetById(p.getPetId()).setBeenAdopted(false);//sets the pet Adoption status to false.
                        } else {
                            throw new PurchaseInvalidInput();
                        }
                    }
                }
            }

            for (ShoppingCart cartie : petOrders.getSubOrders().stream().toArray(ShoppingCart[]::new)) {//filters through the order for the multiple shopping carts

                linePrice = oscDAO.getShoppingCartById(cartie.getCartId()).getLineTotal();
                linePriceOutput = "Line Order:" + linePrice.toString() + "\n" + linePriceOutput;
                total = linePrice + total;
                if (oscDAO.getShoppingCartById(cartie.getCartId()).getItemOrder() != null) {//if the shopping cart has an item, get the item from the inventory repo
                    Integer subtractAmount = oscDAO.getShoppingCartById(cartie.getCartId()).getItemQuantity();
                    Inventory t = oscDAO.getShoppingCartById(cartie.getCartId()).getItemOrder();
                    iInvenDao.getInventoryItemById(t.getInventoryId());
                    if (iInvenDao.getInventoryItemById(t.getInventoryId()).getNumberAvailable() > subtractAmount) {//if the inventory amount is greater than the desired purchase amount
                        iInvenDao.getInventoryItemById(t.getInventoryId()).setNumberAvailable(
                                iInvenDao.getInventoryItemById(
                                        oscDAO.getShoppingCartById(cartie.getCartId()).getItemOrder().getInventoryId()).getNumberAvailable()
                                        - subtractAmount);//Sets the new inventory amount
                    }
                } else if (oscDAO.getShoppingCartById(cartie.getCartId()).getPet() != null) {
                    Pet p = oscDAO.getShoppingCartById(cartie.getCartId()).getPet();
                    if (!petDao.getPetById(p.getPetId()).isBeenAdopted()) {
                        petDao.getPetById(p.getPetId()).setBeenAdopted(true);//sets the pet Adoption status to true.
                    } else {
                        throw new PurchaseInvalidInput();
                    }
                }
            }

            total = Math.floor(total * tax);//Makes sure the total is rounded to the nearest penny
            petOrders.setTotalPrice(total);//sets the total price
            oscDAO.updateOrder(orderId, petOrders);
            strResEnt = new ResponseEntity<>("Line Prices:\n" + linePriceOutput + "\n" + "Purchase Amount Total: \n" + total.toString(), HttpStatus.OK);
            return strResEnt;
        }catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new NotFound();
        }
    }

    /**
     * Updates the shopping cart.
     *
     * @param cartId
     * @param cart
     * @return
     */
    @Override
    public ResponseEntity<String> updateShoppingCart(Integer cartId, ShoppingCart cart) {
        try {
            if (cart.getPet() != null) {
                cart.setLineTotal(petDao.getPetById(cart.getPet().getPetId()).getPetAdoptionFee());
                oscDAO.updateShoppingCart(cartId, cart);
                strResEnt = new ResponseEntity<>(HttpStatus.OK);
                return strResEnt;
            } else if (cart.getItemOrder().getInventoryId() != null) {
                Double lineTotals =
                        iInvenDao.getInventoryItemById(cart.getItemOrder()
                                .getInventoryId()).getPrice();
                cart.setLineTotal(lineTotals * cart.getItemQuantity());
                oscDAO.updateShoppingCart(cartId, cart);
                strResEnt = new ResponseEntity<>(HttpStatus.OK);
                return strResEnt;
            }
            strResEnt = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return strResEnt;
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new NotFound();
        }
    }

    @Override
    public String findTotalSalesByItemType() {
        return oscDAO.findTotalSalesByItemType();
    }

    @Override
    public String findPurchasesInDateRange(Date startDate, Date endDate, int itemId) {
        return oscDAO.findPurchasesInDateRange(startDate, endDate, itemId);
    }
}


