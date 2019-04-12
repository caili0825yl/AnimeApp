package playerstudio.project.User;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class GetRegisterData {

    public static String  post(String username,String password)  {
        OkHttpClient client=new OkHttpClient();
        String data="false";
        String url="http://2324k8108v.iok.la:22557/user/register";

        FormBody body=new FormBody.Builder()
                .add("username",username)
                .add("password",password).build();
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try{
            Response response=client.newCall(request).execute();
             data=response.body().string();
            return data;
        }catch (IOException e){
            e.printStackTrace();
            return data;
        }

    }
}
