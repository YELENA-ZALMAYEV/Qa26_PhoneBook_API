package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ErrorDTO;
import dto.MessageDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIdOkhttp {
    String token = " eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZWx6YWwyODM0QGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzI1MzgwNjQ3LCJpYXQiOjE3MjQ3ODA2NDd9.PJRli6gTbOpDG-sbmLQsT33Ame3bGJ-C42iyi6AUIQY";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    String id;

    @BeforeMethod
    public  void preCondition () throws IOException {
        //create contact
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Maya")
                .lastName("Dow")
                .email("sds"+i+"@gmail.com")
                .phone("645640"+i)
                .address("holon")
                .description("Bee")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDTO),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);
        MessageDTO messageDTO = gson.fromJson(response.body().string(), MessageDTO.class);
        String message = messageDTO.getMessage();
        //get id from "Contct was added!
       String[] all = message.split(": ");
       //id
        id = all[1];
        System.out.println(id);


    }

    @Test
    public  void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/" +id)
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response =client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);

        MessageDTO dto = gson.fromJson(response.body().string(), MessageDTO.class);
        System.out.println(dto.getMessage());
        Assert.assertEquals(dto.getMessage(), "Contact was deleted!");

    }

    @Test
    public  void deleteContactByIdWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/fd1c882d-05ec-4254-87a4-f27df231b5f5")
                .delete()
                .addHeader("Authorization", "jbjhj")
                .build();
        Response response =client.newCall(request).execute();
        Assert.assertEquals(response.code(), 401);

        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
       // System.out.println(dto.getMessage());
        Assert.assertEquals(errorDTO.getError(), "Unauthorized");

    }

    @Test
    public  void deleteContactByIdNotFound() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/" +123)
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response =client.newCall(request).execute();
        Assert.assertEquals(response.code(), 400);

        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(), "Bad Request");
        System.out.println(errorDTO.getMessage());
        Assert.assertEquals(errorDTO.getMessage(), "Contact with id: 123 not found in your contacts!");

    }



}
//75902afb-8dde-499f-890a-490b36745d79
//elzal2834@gmail.com
//=============
//fd1c882d-05ec-4254-87a4-f27df231b5f5
//ezalmtest@gmail.com
//=============
//b9ae148f-733a-4694-9c82-401a2992927a
//Dolly_Sawayn@yahoo.com
//=============
