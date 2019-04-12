package playerstudio.project.News;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import playerstudio.project.R;

public class NewsContentView extends AppCompatActivity {
    private String url;
    private TextView title,content;
    private ImageView image;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newscontent);
        url="http://2324k8108v.iok.la:22557/news/getcontent?listid="+getIntent().getLongExtra("listid",0);
        title=findViewById(R.id.animetitle1);
        content=findViewById(R.id.animecontent1);
        image=findViewById(R.id.animeimage1);
        sendRequest();
    }
    private void sendRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url(url).build();

                    Response response=client.newCall(request).execute();





                    byte[]  b=response.body().bytes();
                    String   responseData = new String(b, "UTF-8");

                    NewsContent content= parse(responseData);
                    NewsContent anime=new NewsContent(content.getTitle(),content.getImage(),content.getContent());

                    show(anime.getTitle(),anime.getImage(),anime.getContent());}
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void show(final String title1,final String image1,final String content1){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RequestOptions options = new RequestOptions().placeholder(R.drawable.loading);

                title.setText(title1);
                Glide.with(image.getContext()).load(image1).  apply(options).into(image);
                content.setText(content1);

            }
        });
    }

    private NewsContent parse(String jsonData){
        Gson gson=new Gson();
        return  gson.fromJson(jsonData, NewsContent.class);
    }

}
