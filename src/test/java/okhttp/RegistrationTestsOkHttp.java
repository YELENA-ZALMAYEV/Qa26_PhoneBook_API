package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTestsOkHttp {

    Gson gson = new Gson();
    public  static  final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public  void  RegistrationSuccess() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("elzal@gmail.com").password("Qaz1234567_&").build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();                          //answer server responce
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200); //anuff one of assert
        AuthResponseDTO responseDTO = gson.fromJson( response.body().string(), AuthResponseDTO.class);

        System.out.println(responseDTO.getToken());

}
    @Test
    public  void  RegistrationUnsuccessWrongEmail() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("elzalgmail.com").password("Qaz1234567_&").build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();                          //answer server responce
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 400);
        ErrorDTO errorDTO = gson.fromJson( response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(), 400);
       // Assert.assertEquals(errorDTO.getMessage(), "Any other error");
        Assert.assertEquals(errorDTO.getPath(), "/v1/user/registration/usernamepassword");
    }

    @Test
    public  void  RegistrationDuplicateUser() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("elzal@gmail.com").password("Qaz1234567_&").build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();                          //answer server responce
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 409);
        ErrorDTO errorDTO = gson.fromJson( response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(), 409);
        // Assert.assertEquals(errorDTO.getMessage(), "Any other error");
        Assert.assertEquals(errorDTO.getPath(), "/v1/user/registration/usernamepassword");

    }


}
