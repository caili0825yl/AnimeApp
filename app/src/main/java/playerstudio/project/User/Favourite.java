package playerstudio.project.User;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import playerstudio.project.HttpUtil;
import playerstudio.project.List.Anime;
import playerstudio.project.List.AnimeAdapter;
import playerstudio.project.R;
import playerstudio.project.gson.GsonAnime;
import playerstudio.project.gson.Utility;

public class Favourite extends AppCompatActivity {
    private static int page=1;
    private RecyclerView recyclerView;
    private List<Anime> animeList;
    private AnimeAdapter adapter;
    private RefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        refreshLayout = (RefreshLayout)findViewById(R.id.swipe_layout);
        refreshLayout.setEnableAutoLoadMore(false);

        recyclerView=(RecyclerView)findViewById(R.id.animelist);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        animeList=new ArrayList<>();
        adapter=new AnimeAdapter(animeList,this);
        recyclerView.setAdapter(adapter);
        refresh();
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refresh();
                refreshLayout.finishLoadMore();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                requestNew();
                page++;
                refreshLayout.finishRefresh();
            }
        });

    }

    public void requestNew(){
        page++;
        // 根据返回到的 URL 链接进行申请和返回数据
        String address = "http://2324k8108v.iok.la:22557/anime/getfavouritelist?start="+page+"&username="+Login.user;   // key
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Favourite.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[]  b = response.body().bytes();;




                String responseText = new String(b, "UTF-8");
                List<GsonAnime> newlist = Utility.parseJsonWithGson(responseText);


                for (GsonAnime news:newlist){
                    Anime anime = new Anime(news.id,news.font,news.name,news.platform,news.url);


                    animeList.add(0,anime);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(0);
                    };
                });
            }




        });

    }

    public void refresh() {
        page=1;
        // 根据返回到的 URL 链接进行申请和返回数据
        String address = "http://2324k8108v.iok.la:22557/anime/getfavouritelist?start="+page+"&username="+Login.user;

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Favourite.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] b = response.body().bytes();
                animeList.clear();
                String responseText = new String(b, "UTF-8");
                List<GsonAnime> newlist = Utility.parseJsonWithGson(responseText);



                for (GsonAnime news:newlist){
                    Anime anime = new Anime(news.id,news.font,news.name,news.platform,news.url);


                    animeList.add(anime);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(0);
                        //      refreshLayout.setRefreshing(false);
                    }

                    ;
                });
            }

        });

    }

    @Override
    public void onBackPressed() {
        AnimeAdapter.result=true;
        super.onBackPressed();
    }
}
