package petEmporium.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import petEmporium.customexceptions.NotFound;
import petEmporium.entities.Inventory;
import petEmporium.entities.PetOrders;
import petEmporium.entities.ShoppingCart;
import petEmporium.interfaces.*;

import java.util.*;

@Component
public class OrdersShoppingCartDAO implements IOrdersShoppingCartDAO {

    @Autowired
    private ICartRepo cartRepo;

    @Autowired
    private IOrdersRepo ordersRepo;

    @Autowired
    private IInventoryRepo invenRepo;

    @Override
    public List<PetOrders> getAllOrders() {
        return ordersRepo.findAll();
    }

    @Override
    public List<ShoppingCart> getAllShoppingCarts() {
        return cartRepo.findAll();
    }

    @Override
    public PetOrders getOrderById(Integer orderId) {
        return ordersRepo.findById(orderId).orElseThrow();
    }

    @Override
    public ShoppingCart getShoppingCartById(Integer orderId) {
        return cartRepo.findById(orderId).orElse(null);
    }

    @Override
    public PetOrders createOrder(PetOrders petOrders) {
        return ordersRepo.save(petOrders);
    }

    @Override
    public ShoppingCart createShoppingCart(ShoppingCart cart) {
        return cartRepo.save(cart);
    }

    @Override
    public PetOrders updateOrder(Integer orderId, PetOrders petOrders) {
        PetOrders petdb = ordersRepo.findById(orderId).orElse(null);
        if (petdb == null) {
            throw new NotFound();
        } else {
            petOrders.setOrderId(orderId);
            return ordersRepo.save(petOrders);
        }
    }

    @Override
    public ShoppingCart updateShoppingCart(Integer cartId, ShoppingCart cart) {
        ShoppingCart petdb = cartRepo.findById(cartId).orElse(null);
        if (petdb == null) {
            throw new NotFound();
        } else {
            cart.setCartId(cartId);
            return cartRepo.save(cart);
        }
    }

    @Override
    public String findTotalSalesByItemType() {
        // get all suborders
        List<ShoppingCart> allCarts = cartRepo.findAll();

        //type Toys
        Double toysTotal = 0.0;
        for (ShoppingCart cart : allCarts) {
            //find if there in an item in the cart
            Inventory item = cart.getItemOrder();
            //if there is an item, get it's type
            if (item != null) {
                String itemType = item.getItemType().getItemType();
                // if it's type is toys, add the cart's line total to the toys total
                if (itemType.equals("Toys")) {
                    Double lineTotal = cart.getLineTotal();
                    toysTotal = toysTotal + lineTotal;
                }
            }

        }

        //type Food
        Double foodTotal = 0.0;
        for (ShoppingCart cart : allCarts) {
            //find if there in an item in the cart
            Inventory item = cart.getItemOrder();
            //if there is an item, get it's type
            if (item != null) {
                String itemType = item.getItemType().getItemType();
                // if it's type is food, add the cart's line total to the food total
                if (itemType.equals("Food")) {
                    Double lineTotal = cart.getLineTotal();
                    foodTotal = foodTotal + lineTotal;
                }
            }
        }

        //type Accessories
        Double accessoriesTotal = 0.0;
        for (ShoppingCart cart : allCarts) {
            //find if there in an item in the cart
            Inventory item = cart.getItemOrder();
            //if there is an item, get it's type
            if (item != null) {
                String itemType = item.getItemType().getItemType();
                // if it's type is accessories, add the cart's line total to the accessories total
                if (itemType.equals("Accessories")) {
                    Double lineTotal = cart.getLineTotal();
                    accessoriesTotal = accessoriesTotal + lineTotal;
                }
            }
        }

        String result = "Toys: $" + toysTotal + ", Food: $" + foodTotal + ", Accessories: $" + accessoriesTotal + ".";
        return result;

    }

    @Override
    public String findPurchasesInDateRange(Date startDate, Date endDate, int itemId) {
        //get all orders that occurred in the date range
        List<PetOrders> allOrders = ordersRepo.findAll();
        List<PetOrders> ordersInRange = new ArrayList<>();
        List<ShoppingCart> cartsInRange = new ArrayList<>();

        // add an order to the list if it within the date range
        for (PetOrders order : allOrders) {
            Date orderDate = order.getCreatedOnDate();
            if (orderDate.after(startDate) && orderDate.before(endDate)) {
                ordersInRange.add(order);
                Set<ShoppingCart> subOrders = order.getSubOrders();
                cartsInRange.addAll(subOrders);
            }
        }

        // find the item in question
        Inventory inItem = invenRepo.findById(itemId).orElse(null);
        if (inItem != null) {
            String name = inItem.getName();
            int totalSold = 0;
            for (ShoppingCart cart : cartsInRange) {
                //find if there in an item in the cart
                Inventory item = cart.getItemOrder();
                //if there is an item, get it's id
                if (item != null) {
                    int id = item.getInventoryId();
                    // if it's the right product, find how many were bought in that suborder and add it to the total
                    if (id == itemId) {
                        int numberSold = cart.getItemQuantity();
                        totalSold = totalSold + numberSold;
                    }
                }
            }

            String result = "Number of " + name + " sold: " + totalSold + ".";
            return result;
        }
        return null;
    }

}
