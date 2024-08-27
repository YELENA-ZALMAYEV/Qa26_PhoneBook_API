package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;

public class LoginTestsOkhttp {

    Gson gson = new Gson();
    public  static  final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();


    @Test
    public  void  loginSuccess() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("elzal2834@gmail.com").password("Qaz12345_&").build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();                          //answer server responce
       Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200); //anuff one of assert
       AuthResponseDTO responseDTO = gson.fromJson( response.body().string(), AuthResponseDTO.class);

        System.out.println(responseDTO.getToken());
    }

    @Test
    public  void  loginWrongEmail() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("molygmail.com").password("@12345qazX").build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();                          //answer server responce
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 401); //anuff one of assert
        ErrorDTO errorDTO = gson.fromJson( response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getClass(), 401);
      //  Assert.assertEquals;
    }}
//
//    @Test
//    public void loginWrongPassword() throws IOException {
//        AuthRequestDTO auth = AuthRequestDTO.builder().username("mara@gmail.com").password("Mmar123").build();
//        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
//        Request request = new Request.Builder()
//                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
//                .post(body)
//                .build();
//        Response response = client.newCall(request).execute();
//
//        Assert.assertEquals(response.code(), 401);
//        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
//        Assert.assertEquals(errorDTO.getStatus(), 401);
//        Assert.assertEquals(errorDTO.getMessage(), "Login or Password incorrect");
//        Assert.assertEquals(errorDTO.getPath(), "/v1/user/login/usernamepassword");
//    }
//
//    @Test
//    public void loginUnregisteredUser() throws IOException {
//        AuthRequestDTO auth = AuthRequestDTO.builder().username("mara1345@gmail.com").password("Mmar123456$").build();
//        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
//        Request request = new Request.Builder()
//                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
//                .post(body)
//                .build();
//        Response response = client.newCall(request).execute();
//        Assert.assertEquals(response.code(), 401);
//        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
//        Assert.assertEquals(errorDTO.getStatus(), 401);
//        Assert.assertEquals(errorDTO.getMessage(), "Login or Password incorrect");
//        Assert.assertEquals(errorDTO.getPath(), "/v1/user/login/usernamepassword");
//    }
//}

