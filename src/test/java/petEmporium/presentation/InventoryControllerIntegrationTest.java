package petEmporium.presentation;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.google.gson.Gson;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import petEmporium.entities.Inventory;
import petEmporium.entities.ItemType;

import javax.servlet.ServletContext;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    static Inventory testItem;
    static ItemType type;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

        type = new ItemType("Accessories");
        type.setItemTypeId(3);
        testItem = new Inventory("Test", type, "12345678", 12, 12.99);
        testItem.setInventoryId(3);
    }

    //Verify test configuration
    @Test
    public void checkContext() {

        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertTrue(((GenericWebApplicationContext) wac).isActive());
    }


    @Test
    public void testAddInventoryItem() throws Exception {

        Gson gson = new Gson();
        String json = gson.toJson(testItem);

        mockMvc.perform(post("/inventory").contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku").value(testItem.getSku()));
    }

    @Test
    public void testGetAllInventoryItems() throws Exception {
        mockMvc.perform(get("/inventory"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))); // has size 3 since the data loader starts with 2 items
    }

    @Test
    public void testGetInventoryItemById() throws Exception {
        mockMvc.perform(get("/inventory/" + testItem.getInventoryId()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value(testItem.getSku()));
    }

    @Test
    public void testGetInventoryItemByName() throws Exception {
        mockMvc.perform(get("/inventory/name/" + testItem.getName()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value(testItem.getSku()));
    }

    @Test
    public void testGetInventoryItemsByType() throws Exception {
        String stringType = testItem.getItemType().getItemType();
        mockMvc.perform(get("/inventory/type/" + stringType))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))); // has size 1 since the data loader didn't load any items with type "Accessories"
    }

    @Test
    public void testGetInventoryItemBySku() throws Exception {
        mockMvc.perform(get("/inventory/sku/" + testItem.getSku()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value(testItem.getSku()));
    }

    @Test
    public void testUpdateInventoryItem() throws Exception {
        Gson gson = new Gson();

        //update name
        testItem.setName("New Name");
        String json = gson.toJson(testItem);

        mockMvc.perform(put("/inventory/" + testItem.getInventoryId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());

        //Confirm name is updated
        mockMvc.perform(get("/inventory/" + testItem.getInventoryId()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testItem.getName()));

    }

    @Test
    public void testzDeleteInventoryItem() throws Exception {

        mockMvc.perform(delete("/inventory/" + testItem.getInventoryId()));


        //Confirm item is deleted
        mockMvc.perform(get("/inventory"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // has size 2 since the data loader started with 2 items


    }
}