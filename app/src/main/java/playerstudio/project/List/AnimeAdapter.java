package playerstudio.project.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import playerstudio.project.Mytoast;
import playerstudio.project.R;
import playerstudio.project.User.Login;
import playerstudio.project.User.User;

import static android.support.constraint.Constraints.TAG;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder> {
    private List<Anime> animeList;
  static   public Context mContext;
    static String username;
   public static boolean result=true;
    private static final int COMPLETED = 0;
    private static final int Fail = 1;
    private static final int DELETE = 2;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==COMPLETED){
                Mytoast.showToast(mContext,"收藏成功！");

            }else if(msg.what==Fail){
                Mytoast.showToast(mContext,"重复收藏！");

            }else {
                Mytoast.showToast(mContext,"删除成功！请刷新！");

            }
        }
    };



    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView Aimage;
        TextView Aname,Atv;
        ImageButton add,play,minus;
        View animeView;
        public ViewHolder(View view){
            super(view);
            animeView=view;
            Aimage=(ImageView)view.findViewById(R.id.Aimage);
            Aname=(TextView)view.findViewById(R.id.Aname);
            Atv=(TextView)view.findViewById(R.id.Atv);
            add=(ImageButton)view.findViewById(R.id.add);
            play=(ImageButton)view.findViewById(R.id.play);
            minus=(ImageButton)view.findViewById(R.id.minus);

        }

    }

    public AnimeAdapter(List<Anime> animelist,Context context){
        this.animeList=animelist;
        this.mContext=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view=LayoutInflater.from(mContext).inflate(R.layout.anime_item,viewGroup,false);

            ViewHolder holder=new ViewHolder(view);
            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=holder.getAdapterPosition();
                    Anime anime=animeList.get(position);
                    Intent intent=new Intent(mContext,Web.class);
                    intent.putExtra("url",anime.getUrl());
                    mContext.startActivity(intent);
                }
            });
        if(result==false){
            holder.add.setVisibility(View.INVISIBLE);
            holder.minus.setVisibility(View.VISIBLE);
        }
     holder.animeView.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {


             int position=holder.getAdapterPosition();
             Anime anime=animeList.get(position);
            Intent intent=new Intent(mContext,ContentView.class);

          intent.putExtra("id",anime.getId());
             mContext.startActivity(intent);

         }
     });
holder.add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(Login.isLogin==true){
            int position=holder.getAdapterPosition();
            Anime anime=animeList.get(position);
            OkHttpClient client=new OkHttpClient();
            String url="http://2324k8108v.iok.la:22557/user/add";
            username=new Intent(mContext,ContentView.class).getStringExtra("username");
            String id=String.valueOf(anime.getId());
            FormBody body=new FormBody.Builder()
                    .add("anime",id)
                    .add("username",Login.user).build();
            Request request=new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Runnable networkTask = new Runnable() {
                @Override
                public void run() {
                    // TODO
                    try{
                      Response response=client.newCall(request).execute();
                        Message msg = new Message();
                        if("success".equals(response.body().string())){
                            msg.what = COMPLETED;
                        }else {
                            msg.what = Fail;
                        }

                        handler.sendMessage(msg);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            };
            new Thread(networkTask).start();
        }else {
            Mytoast.showToast(mContext,"请登录！");
            Intent intent=new Intent(mContext,Login.class);
            mContext.startActivity(intent);
        }








    }

});

holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    int position=holder.getAdapterPosition();
                    Anime anime=animeList.get(position);
                    OkHttpClient client=new OkHttpClient();
                    String url="http://2324k8108v.iok.la:22557/user/minus";
                    username=new Intent(mContext,ContentView.class).getStringExtra("username");
                    String id=String.valueOf(anime.getId());
                    FormBody body=new FormBody.Builder()
                            .add("anime",id)
                            .add("username",Login.user).build();
                    Request request=new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    Runnable networkTask = new Runnable() {
                        @Override
                        public void run() {
                            // TODO
                            try{
                                client.newCall(request).execute();
                                Message msg = new Message();
                                    msg.what = DELETE;


                                handler.sendMessage(msg);
                            }catch (IOException e){
                                e.printStackTrace();
                            }

                        }
                    };
                    new Thread(networkTask).start();









            }

        });

        return holder;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Anime anime=animeList.get(i);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.loading);

        Glide.with(mContext).load(animeList.get(i).getFont())
                .apply(options) .into(viewHolder.Aimage);
        viewHolder.Aname.setText(anime.getName());
        viewHolder.Atv.setText(anime.getPlatform());

    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

}
