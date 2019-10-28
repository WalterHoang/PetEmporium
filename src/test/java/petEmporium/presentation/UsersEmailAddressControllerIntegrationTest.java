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
import petEmporium.customexceptions.EmailNotUnique;
import petEmporium.entities.Address;
import petEmporium.entities.PetEmail;
import petEmporium.entities.PetUsers;

import javax.servlet.ServletContext;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersEmailAddressControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    static Address testAddress;
    static PetEmail testEmail;
    static PetUsers testUser;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

        testAddress = new Address("5555 test Street", null, "Test City", "CO", "88888");
        testAddress.setAddressId(3);


        testEmail = new PetEmail("test7@email.com", "Customer");
        testEmail.setEmailId(3);

        testUser = new PetUsers("test", "Testerson", "555-555-5555", testEmail, Set.of(testAddress));
        testUser.setPetUsersId(3);
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
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users/allUsers"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))); // has size 3 since the data loader starts with 2 items
    }

    @Test
    public void testGetAllEmails() throws Exception {
        mockMvc.perform(get("/users/allEmails"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))); // has size 3 since the data loader starts with 2 items
    }

    @Test
    public void testGetAllAddress() throws Exception {
        mockMvc.perform(get("/users/allAddresses"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))); // has size 3 since the data loader starts with 2 items
    }

    @Test
    public void testCreateUsers() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(testUser);

        mockMvc.perform(post("/users/addUser").contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateAddress() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(testAddress);

        mockMvc.perform(post("/users/addAddress").contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateEmail() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(testEmail);
        testEmail.setEmailId(4);
        String json2 = gson.toJson((testEmail));

        mockMvc.perform(post("/users/addEmail").contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateEmailSad() throws Exception{
        Gson gson = new Gson();
        testEmail.setEmailId(4);
        String json2 = gson.toJson((testEmail));

        mockMvc.perform(post("/users/addEmail").contentType(APPLICATION_JSON_UTF8)
                .content(json2)).andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateUsers() throws Exception {
        Gson gson = new Gson();

        //update first name
        testUser.setFirstName("New Name");
        String json = gson.toJson(testUser);

        mockMvc.perform(put("/users/updateUser" + testUser.getPetUsersId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());

        //Confirm name is updated
        mockMvc.perform(get("/users/user" + testUser.getPetUsersId()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(testUser.getFirstName()));
    }

    @Test
    public void testUpdateAddress() throws Exception {
        Gson gson = new Gson();

        //update cite
        testAddress.setCityAddress("New City");
        String json = gson.toJson(testAddress);

        mockMvc.perform(put("/users/updateAddress" + testAddress.getAddressId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());

        //Confirm city is updated
        mockMvc.perform(get("/users/address" + testAddress.getAddressId()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityAddress").value(testAddress.getCityAddress()));
    }

    @Test
    public void testUpdateEmail() throws Exception {
        Gson gson = new Gson();

        //update email
        testEmail.setUserEmail(".andExpect(jsonPath(\"$.firstName\").value(testUser.getFirstName()));");
        String json = gson.toJson(testEmail);

        mockMvc.perform(put("/users/updateEmail" + testEmail.getEmailId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());

        //Confirm name is updated
        mockMvc.perform(get("/users/email" + testEmail.getEmailId()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userEmail").value(testEmail.getUserEmail()));
    }

    @Test
    public void testzaDeleteUsers() throws Exception {
        mockMvc.perform(delete("/users/deleteUser" + testUser.getPetUsersId()));


        //Confirm item is deleted
        mockMvc.perform(get("/users/allUsers"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // has size 2 since the data loader started with 2 items
    }

    @Test
    public void testzbDeleteAddress() throws Exception {
        mockMvc.perform(delete("/users/deleteAddress" + testAddress.getAddressId()));


        //Confirm item is deleted
        mockMvc.perform(get("/users/allAddresses"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // has size 2 since the data loader started with 2 items
    }

    @Test
    public void testzcDeleteEmail() throws Exception {
        mockMvc.perform(delete("/users/deleteEmail" + testEmail.getEmailId()));


        //Confirm item is deleted
        mockMvc.perform(get("/users/allEmails"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // has size 2 since the data loader started with 2 items
    }

    @Test
    public void testGetByUsersId() throws Exception {
        mockMvc.perform(get("/users/user" + testUser.getPetUsersId()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(testUser.getFirstName()));
    }

    @Test
    public void testGetByAddressId() throws Exception {
        mockMvc.perform(get("/users/address" + 3))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.streetAddress").value(testAddress.getStreetAddress()));
    }

    @Test
    public void testGetByEmailId() throws Exception {
        mockMvc.perform(get("/users/email" + testUser.getEmail().getEmailId()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userEmail").value(testEmail.getUserEmail()));
    }

    @Test
    public void testGetByUsersPhoneNumber() throws Exception {
        mockMvc.perform(get("/users/userbyphonenumber/?phoneNumber=" + testUser.getPhoneNumber()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(testUser.getFirstName()));
    }

    @Test
    public void testGetByUsersLastName() throws Exception {
        mockMvc.perform(get("/users/userbylastname/?lastName=" + testUser.getLastName()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
        // .andExpect(jsonPath("$", hasSize(1))); // has size 1 since the data loader didn't load anyone with last name "Testerson"
    }

    @Test
    public void testGetByUsersEmail() throws Exception {
        mockMvc.perform(get("/users/userbyemail/?userEmail=" + testUser.getEmail().getUserEmail()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
        //.andExpect(jsonPath("$.firstName").value(testUser.getFirstName()));
    }
}