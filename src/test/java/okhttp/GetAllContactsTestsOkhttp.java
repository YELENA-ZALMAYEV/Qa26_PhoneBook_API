package okhttp;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetAllContactsTestsOkhttp {

    String token = " eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZWx6YWwyODM0QGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzI1MzgwNjQ3LCJpYXQiOjE3MjQ3ODA2NDd9.PJRli6gTbOpDG-sbmLQsT33Ame3bGJ-C42iyi6AUIQY";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();


    @Test
    public  void getAllContactSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public  void getAllContactsWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization", "123Sd_Q")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 401);
    }
}
