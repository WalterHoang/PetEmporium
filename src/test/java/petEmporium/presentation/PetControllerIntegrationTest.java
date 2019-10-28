package petEmporium.presentation;

import com.google.gson.Gson;
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
import petEmporium.entities.Pet;

import javax.servlet.ServletContext;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    static Pet testPet;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

        testPet = new Pet("test", "Bird", "M", 100.99, null, null, true);
        testPet.setPetId(3);
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
    public void testAddPet() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(testPet);

        mockMvc.perform(post("/pets").contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.petName").value(testPet.getPetName()));
    }

    @Test
    public void testGetallPets() throws Exception {
        mockMvc.perform(get("/pets/allPets"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))); // has size 2 since the data loader starts with 2 items
    }

    @Test
    public void testGetAllPetsByName() throws Exception {
        mockMvc.perform(get("/pets/petName?petName=" + testPet.getPetName()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))); // has size 1 since the data loader didn't load a pet named "test"
    }

    @Test
    public void testGetAllPetsByType() throws Exception {
        mockMvc.perform(get("/pets/petType?petType=" + testPet.getPetType()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))); // has size 1 since the data loader didn't load any items with type "Accessories"
    }

    @Test
    public void testGetPetById() throws Exception {
        mockMvc.perform(get("/pets/" + testPet.getPetId()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petName").value(testPet.getPetName()));
    }

    @Test
    public void testUpdatePet() throws Exception {
        Gson gson = new Gson();

        //update name
        testPet.setPetName("New Name");
        String json = gson.toJson(testPet);

        mockMvc.perform(put("/pets/" + testPet.getPetId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());

        //Confirm name is updated
        mockMvc.perform(get("/pets/" + testPet.getPetId()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petName").value(testPet.getPetName()));

    }

    @Test
    public void testzDeletePet() throws Exception {
        mockMvc.perform(delete("/pets/" + testPet.getPetId()));


        //Confirm item is deleted
        mockMvc.perform(get("/pets/allPets"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // has size 2 since the data loader started with 2 items


    }
}