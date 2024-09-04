package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UpDateExistsContactRA  {
    String token = " eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZWx6YWwyODM0QGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzI2MDMxMjQ2LCJpYXQiOjE3MjU0MzEyNDZ9.AIoyZZmedBw2LlM_eP_ZB7ndyOJV2QTUZWgXjRSkukM";

        ContactDTO contactDTO = ContactDTO.builder()
                .name("dona")
                .lastName("doww")
                .email("don@gmail.com")
                .phone("1235671238")
                .address("Tel Aviv")
                .description("donna")
                .build();
        String id;


    @BeforeMethod
    public void preCondition(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

        String message = given()
                .header("Authorization", token)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("message");
        String[] all = message.split(": ");
        id = all[1];
    }

    @Test
    public  void  updateExistsContactSuccess(){
        String name = contactDTO.getName();
        contactDTO.setId(id);
        contactDTO.setName("wwwwww");

        given()
                .body(contactDTO)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("Contact was updated"));


    }
}
