package petEmporium.dataLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import petEmporium.entities.*;
import petEmporium.interfaces.*;

import java.util.HashSet;
import java.util.Set;

/**
 * This class runs after the server starts and executes methods that load initial datasets into the db
 */
@Component
public class DataLoaderRunner implements CommandLineRunner {
    @Autowired
    private IAddressRepo addressRepo;
    @Autowired
    private IPetEmailRepo petEmailRepo;
    @Autowired
    private IPetUsersRepo petUsersRepo;
    @Autowired
    private IInventoryRepo inventoryRepo;
    @Autowired
    private IItemTypeRepo itemTypeRepo;
    @Autowired
    private IPetRepo petRepo;
    @Autowired
    private ICartRepo cartRepo;
    @Autowired
    private IOrdersRepo ordersRepo;

    private Address address1;
    private Address address2;
    Set<Address> addresses = new HashSet<>();
    Set<Address> addresses1 = new HashSet<>();


    private PetEmail petEmail1;
    private PetEmail petEmail2;

    private PetUsers petUsers1;
    private PetUsers petUsers2;

    private ItemType itemtype1 = new ItemType("Food");
    private ItemType itemType2 = new ItemType("Toys");

    private Inventory inventory1;
    private Inventory inventory2;

    private Pet pet1;
    private Pet pet2;

    private PetOrders petOrder1;
    private PetOrders petOrder2;

    private ShoppingCart cart1;
    private ShoppingCart cart2;
    Set<ShoppingCart> carts1 = new HashSet<>();
    Set<ShoppingCart> carts2 = new HashSet<>();

    @Override
    public void run(String... args) throws Exception {
        loadAddresses();
        loadEmails();
        loadUsers();
        loadPets();
        loadInventory();
        loadCart();
        loadOrder();
    }

    private void loadAddresses() {
        address1 = addressRepo.save(new Address("4444 test Street", null, "Test City", "CO", "88888"));
        address2 = addressRepo.save(new Address("1111 rage court", null, "Test City", "VT", "23334"));
        addresses.add(address1);
        addresses1.add(address2);
    }

    private void loadEmails() {
        petEmail1 = petEmailRepo.save(new PetEmail("TurtleStomper1337@msk.com", "Customer"));
        petEmail2 = petEmailRepo.save(new PetEmail("italianpriest@lgim.com", "Customer"));
    }

    private void loadUsers() {

        petUsers1 = petUsersRepo.save(new PetUsers("Mario", "Mario", "344-657-0443", petEmail1, addresses));
        petUsers2 = petUsersRepo.save(new PetUsers("Sanic", "Maurice", "907-709-0527", petEmail2, addresses1));

    }

    private void loadPets() {
        pet1 = petRepo.save(new Pet("Miles Prowler", "Cat", "M", 100.99, null, null, true));
        pet2 = petRepo.save(new Pet("Zeus", "Dog", "M", 110.85, "Gold", "2 months", true));
    }

    private void loadInventory() {
        itemTypeRepo.save(itemtype1);
        itemTypeRepo.save(itemType2);
        inventory1 = inventoryRepo.save(new Inventory("Chili Cheese Dogs", itemtype1, "111a3d12", 10, 10.99));
        inventory2 = inventoryRepo.save(new Inventory("Chew Toy", itemType2, "a332d900d", 50, 6.99));
    }

    private void loadCart() {
        cart1 = new ShoppingCart();
        cart1.setLineTotal(32.97);
        cart1.setItemOrder(inventory1);
        cart1.setItemQuantity(3);
        cartRepo.save(cart1);
        cart2 = new ShoppingCart();
        cart2.setPet(pet1);
        cart2.setLineTotal(100.99);
        cartRepo.save(cart2);
        carts1.add(cart1);
        carts2.add(cart2);
    }

    private void loadOrder() {
        petOrder1 = new PetOrders();
        petOrder1.setPetUsers(petUsers1);
        petOrder1.setSubOrders(carts1);
        petOrder1.setTotalPrice(32.97);
        ordersRepo.save(petOrder1);
        petOrder2 = new PetOrders();
        petOrder2.setPetUsers(petUsers2);
        petOrder2.setSubOrders(carts2);
        petOrder2.setTotalPrice(100.99);
        ordersRepo.save(petOrder2);
    }
}
