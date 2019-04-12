package playerstudio.project.List;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import playerstudio.project.HttpUtil;
import playerstudio.project.News.News;
import playerstudio.project.R;
import playerstudio.project.gson.GsonAnime;
import playerstudio.project.gson.GsonAnimelist;
import playerstudio.project.gson.GsonNews;
import playerstudio.project.gson.Utility;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListMain extends Fragment {
    private static int page=1;
    private RecyclerView recyclerView;
    private List<Anime> animeList;
    private AnimeAdapter adapter;
    private RefreshLayout refreshLayout;
    public ListMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.list, container, false);
        refreshLayout = (RefreshLayout)view.findViewById(R.id.swipe_layout);
        refreshLayout.setEnableAutoLoadMore(false);

        recyclerView=(RecyclerView)view.findViewById(R.id.animelist);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        animeList=new ArrayList<>();
adapter=new AnimeAdapter(animeList,view.getContext());
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
return  view;
    }



    public void requestNew(){
        page++;
        // 根据返回到的 URL 链接进行申请和返回数据
        String address = "http://2324k8108v.iok.la:22557/anime/getlist?start="+page;   // key
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
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

                    getActivity().runOnUiThread(new Runnable() {
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
        String address = "http://2324k8108v.iok.la:22557/anime/getlist?start="+page;

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
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

                getActivity().runOnUiThread(new Runnable() {
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

}
