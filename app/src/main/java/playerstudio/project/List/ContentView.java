package playerstudio.project.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import playerstudio.project.R;

public class ContentView extends AppCompatActivity {
    private TextView title,animecontent,tv,staff,voice;
    private ImageView image;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animecontent);

        animecontent=(TextView)findViewById(R.id.animecontent);
        tv=findViewById(R.id.animetv);
        staff=findViewById(R.id.animestaff);
        voice=findViewById(R.id.animevoice);
        title=(TextView)findViewById(R.id.animetitle);
        image=findViewById(R.id.animefont);

        sendRequest();


    }

   private void sendRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://2324k8108v.iok.la:22557/anime/getcontent?listid="+getIntent().getLongExtra("id",0)).build();

                    Response response=client.newCall(request).execute();





                    byte[]  b=response.body().bytes();
                    String   responseData = new String(b, "UTF-8");
                    Content content= parse(responseData);
                    Content anime=new Content(content.getImage(),content.getName(),content
                            .getTv(),content.getStaff(),content.getVoice(),content.getJs());

                    show(anime.getName(),anime.getImage(),anime.getTv(),anime.getStaff(),anime.getVoice(),anime.getJs());

                }

                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void show(String f, String a,String b,String c,String d,String e){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RequestOptions options = new RequestOptions().placeholder(R.drawable.loading);
                tv.setText(b);
                staff.setText(c);
                voice.setText(d);
                title.setText(f);
                Glide.with(ContentView.this).load(a)
                        .apply(options) .into(image);

                animecontent.setText(e);

            }
        });
    }

    private Content parse(String jsonData){
        Gson gson=new Gson();
return  gson.fromJson(jsonData,Content.class);
    }

}
