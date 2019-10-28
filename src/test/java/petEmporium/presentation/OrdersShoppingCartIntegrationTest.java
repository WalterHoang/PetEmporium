package petEmporium.presentation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import petEmporium.entities.*;
import petEmporium.interfaces.*;

import javax.servlet.ServletContext;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdersShoppingCartIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private IAddressRepo addressRepo;
    @Autowired
    private IPetEmailRepo emailRepo;
    @Autowired
    private IPetUsersRepo usersRepo;

    @Autowired
    private IItemTypeRepo itemTypeRepo;
    @Autowired
    private IInventoryRepo inventoryRepo;

    @Autowired
    private IPetRepo petRepo;

    private static Address testAddress;
    private static PetEmail testEmail;
    private static PetUsers testUser;

    private static ItemType testType;
    private static Inventory testItem;

    private static Pet testPet;

    private static ShoppingCart testCart1;
    private static ShoppingCart testCart2;
    private static PetOrders testOrder;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

        testAddress = new Address("5555 test Street", null, "Test City", "CO", "88888");
        addressRepo.save(testAddress);

        testEmail = new PetEmail("test7@email.com", "Customer");
        emailRepo.save(testEmail);

        testUser = new PetUsers("test", "Testerson", "555-555-5555", testEmail, Set.of(testAddress));
        usersRepo.save(testUser);

        testType = new ItemType("Accessories");
        itemTypeRepo.save(testType);

        testItem = new Inventory("Test", null, "12345678", 12, 12.99);
        inventoryRepo.save(testItem);

        testPet = new Pet("pet name", "Bird", "F", 100.00, null, null, false);
        petRepo.save(testPet);

        testCart1 = new ShoppingCart(testItem, 1, null, 12.99);
        testCart2 = new ShoppingCart(testPet, null, 100.00);

        testCart1.setCartId(3);
        testCart2.setCartId(4);

        Set<ShoppingCart> carts = new HashSet<ShoppingCart>();
        carts.add(testCart1);
        carts.add(testCart2);

        testOrder = new PetOrders(null, testUser, carts, null, null, null);

    }

    //Verify test configuration
    @Test
    public void AcheckContext() {

        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertTrue(((GenericWebApplicationContext) wac).isActive());
    }

    @Test
    public void DgetAllShoppingCarts() throws Exception {
        mockMvc.perform(get("/orders/allSubOrders"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4))); // data loader loads 2
    }

    @Test
    public void DgetAllOrders() throws Exception {
        mockMvc.perform(get("/orders/getAllOrders"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))); // data loader loads 2
    }

    @Test
    public void CgetShoppingCartById() throws Exception {
        mockMvc.perform(get("/orders/getSubOrder1"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lineTotal").value(32.97));
    }

    @Test
    public void CgetOrderById() throws Exception {
        mockMvc.perform(get("/orders/getOrders1"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(32.97));
    }

    @Test
    public void BcreateOrder() throws Exception {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = gson.toJson(testOrder);

        mockMvc.perform(post("/orders/createOrder").contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());

    }

    @Test
    public void BbcreateShoppingCart1() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(testCart1);

        mockMvc.perform(post("/orders/createShoppingCart").contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void BbcreateShoppingCart2() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(testCart2);
        mockMvc.perform(post("/orders/createShoppingCart").contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateOrder() throws Exception {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        Set<ShoppingCart> testUpdatesCarts = new HashSet<ShoppingCart>();
        testUpdatesCarts.add(testCart1);

        //update user
        testUser.setFirstName("Timmy");
        testOrder.setPetUsers(testUser);
        testOrder.setSubOrders(testUpdatesCarts);
        testOrder.setOrderId(3);
        String json = gson.toJson(testOrder);

        mockMvc.perform(put("/orders/updateOrder" + testOrder.getOrderId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());

//        //Confirm user is updated
//        mockMvc.perform(get("/orders/getOrders" + testOrder.getOrderId()))
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.petUser.firstName").value(testUser.getFirstName()));
    }

    @Test
    public void updateShoppingCart() throws Exception {
        Gson gson = new Gson();
        testCart1.setItemQuantity(3);
        testCart1.setCartId(3);
        String json = gson.toJson(testCart1);

        mockMvc.perform(put("/orders/updateShopping" + testCart1.getCartId())
                .contentType(APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void ZdeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/deleteOrder")).andExpect(status().isForbidden());
    }

    @Test
    public void ZdeleteShoppingCart() throws Exception {
        mockMvc.perform(delete("/orders/deleteSubOrder")).andExpect(status().isForbidden());
    }


}
