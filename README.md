# Pet Emporium API
This is a RESTFUL API for a pet emporium that supports CRUD functionality, validation, and high level domain searches.

This program comes with a dataloader that prepopulates the database with 2 rows of data for each entity.


### Getting Started

These instructions will get you a copy of the project up and running on your local
machine for development and testing purposes.

1. Create a Postgresql database with the same name in the application.yml file
2. Import the pet Emporium project in IntelliJ.
3. Run the application.
4. Use the application.yml file to configure the initial actions once the program is running for future runs.
5. Set ddl-auto from create-drop to none and remove the dataloader package from the file path if you do not want to run the
   dataloader on subsequent runs.

### Swagger

To use the Swagger UI, navigate to [http://localhost:8080/swagger-ui.html#/](http://localhost:8080/swagger-ui.html#/)


### Running the Unit Tests
1. Make sure the java folder under test is marked as the Test Sources Root.
2. Right click on the java folder under test and click Run 'All Tests' or Run 'All Tests' with Coverage.

### Postman Collection
1. Open Postman.
2. On the top left-hand corner, click Import.
3. Select the tab for Import From Link, insert the url: https://www.getpostman.com/collections/7038a56e15e03087859d and hit Import.