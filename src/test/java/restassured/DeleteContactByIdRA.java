package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdRA {

    String token = " eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZWx6YWwyODM0QGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzI2MDMxMjQ2LCJpYXQiOjE3MjU0MzEyNDZ9.AIoyZZmedBw2LlM_eP_ZB7ndyOJV2QTUZWgXjRSkukM";
    String id;

    @BeforeMethod
    public void preCondition(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";


    int i = new Random().nextInt(1000) +1000;

    ContactDTO contactDTO = ContactDTO.builder()
            .name("dona")
            .lastName("doww")
            .email("don"+i+"@gmail.com")
            .phone("1235678"+i)
            .address("Tel Aviv")
            .description("donna")
            .build();

    String message = given()
            .body(contactDTO)
            .contentType(ContentType.JSON)
            .header("Authorization", token)
            .post("contacts")
            .then()
            .assertThat().statusCode(200)
            .extract()
            .path("message");
    String[] all = message.split(": ");
    id = all[1];
}

@Test
    public  void deleteContactById(){
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));


}

    @Test
    public  void deleteContactWrongToken(){
        given()
                .header("Authorization", "jhkfh")
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(401);

    }

    @Test
    public  void deleteContactByAnyFormatError(){
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/123" )
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Contact with id: 123 not found in your contacts!"));

    }








}
