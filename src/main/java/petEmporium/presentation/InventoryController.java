package petEmporium.presentation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;
import petEmporium.customexceptions.ExceptionResponse;
import petEmporium.customexceptions.FriendlyServerErrorException;
import petEmporium.entities.Inventory;
import petEmporium.interfaces.IInventoryService;

import java.util.List;

/**
 * this is the presentation layer for the inventory class, where we define what happens for certain HTTP requests
 */
@RestController
@RequestMapping(value = "/inventory")
@ApiResponses(value = {@ApiResponse(code = 500, message = "An unexpected error occurred.")})
public class InventoryController {

    /**
     * instantiate an inventory service so we can use its validated methods
     */
    @Autowired
    private IInventoryService inventoryService;

    private static Logger logger = LogManager.getLogger();

    /**
     * adds a new inventory item to the database
     *
     * @param inventory the inventory item to add
     * @return the new inventory item as well as proper status code
     */
    @PostMapping
    @ApiOperation("Posts a new Inventory item to the system.") // For swagger
    @ApiResponses(value = { // response codes for swagger
            @ApiResponse(code = 201, message = "Created", response = Inventory.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class)
    })
    public ResponseEntity<Inventory> addInventoryItem(@RequestBody Inventory inventory) {
        try {
            return new ResponseEntity<>(inventoryService.addInventoryItem(inventory), HttpStatus.CREATED);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }


    /**
     * returns a list of all inventory items
     *
     * @return
     */
    @GetMapping
    @ApiOperation("Gets all Inventory items in the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Inventory.class)
    })
    public ResponseEntity<List<Inventory>> getAllInventoryItems() {
        try {
            return new ResponseEntity<>(inventoryService.getAllInventoryItems(), HttpStatus.OK);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }


    /**
     * returns the inventory item with the corresponding id
     *
     * @param inventoryItemId
     * @return
     */
    @GetMapping(value = "/{inventoryItemId}")
    @ApiOperation("Gets a inventory item in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Inventory.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class)
    })
    public ResponseEntity<Inventory> getInventoryItemById(@PathVariable int inventoryItemId) {
        try {
            return new ResponseEntity<>(inventoryService.getInventoryItemById(inventoryItemId), HttpStatus.OK);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }


    /**
     * returns the FIRST inventory item with the given name
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/name/{name}")
    @ApiOperation("Gets a inventory item in the system by name.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Inventory.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class)
    })
    public ResponseEntity<Inventory> getInventoryItemByName(@PathVariable String name) {
        try {
            return new ResponseEntity<>(inventoryService.getInventoryItemByName(name), HttpStatus.OK);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }


    /**
     * returns a list of all inventory items of the given type (NOT typeId)
     *
     * @param type
     * @return
     */
    @GetMapping(value = "/type/{type}")
    @ApiOperation("Gets a list of inventory item in the system by type.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Inventory.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class)
    })
    public ResponseEntity<List<Inventory>> getInventoryItemsByType(@PathVariable String type) {
        try {
            return new ResponseEntity<>(inventoryService.getInventoryItemsByType(type), HttpStatus.OK);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }


    /**
     * returns the inventory item with the given sku
     *
     * @param sku
     * @return
     */
    @GetMapping(value = "/sku/{sku}")
    @ApiOperation("Gets a inventory item in the system by sku.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Inventory.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class)
    })
    public ResponseEntity<Inventory> getInventoryItemBySku(@PathVariable String sku) {
        try {
            return new ResponseEntity<>(inventoryService.getInventoryItemBySku(sku), HttpStatus.OK);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }


    /**
     * returns an updated inventory item, given the id of the item you want to update and the information you wish to be associated with that id
     *
     * @param inventoryItemId
     * @param inventory
     * @return
     */
    @PutMapping(value = "/{inventoryItemId}")
    @ApiOperation("Updates a Inventory item in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Inventory.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 409, message = "A conflict occurred", response = ExceptionResponse.class)
    })
    public ResponseEntity<Inventory> updateInventoryItem(@PathVariable int inventoryItemId, @RequestBody Inventory inventory) {
        try {
            return new ResponseEntity<>(inventoryService.updateInventoryItem(inventoryItemId, inventory), HttpStatus.OK);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }


    /**
     * deletes the inventory item with the given id from the database
     *
     * @param inventoryItemId
     */
    @DeleteMapping(value = "/{inventoryItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Deletes a Inventory item in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 409, message = "Conflict Occurred", response = ExceptionResponse.class)
    })
    public void deleteInventoryItem(@PathVariable int inventoryItemId) {
        try {
            inventoryService.deleteInventoryItem(inventoryItemId);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }
}
