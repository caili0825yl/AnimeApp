package playerstudio.project.News;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import playerstudio.project.HttpUtil;
import playerstudio.project.R;
import playerstudio.project.gson.AnimeN;
import playerstudio.project.gson.GsonAnimeNList;
import playerstudio.project.gson.GsonNews;
import playerstudio.project.gson.GsonNewslist;
import playerstudio.project.gson.Utility;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComicNews extends Fragment {
    private static int page=1;

    private ArrayList<String> images;
    private ArrayList<String> titles;
    private Banner banner;
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private Intent intent;
    private RefreshLayout refreshLayout;
    private java.util.List<News> List;

    public ComicNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.anime_news, container, false);
        banner = (Banner) view.findViewById(R.id.banner);
        images = new ArrayList<>();
        titles = new ArrayList<>();
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);

        // Inflate the layout for this fragment


        //设置图片加载器
        banner.setImageLoader(new MyLoader());
        //设置图片集合



        //banner设置方法全部调用完毕时最后调用



        pd();









        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        refreshLayout.setEnableAutoLoadMore(false);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        List = new ArrayList<>();
        mRecyclerView.setLayoutManager(layoutManager);
        //     mRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        mAdapter = new NewsAdapter(List, view.getContext());

        mRecyclerView.setAdapter(mAdapter);
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


        return view;
    }

    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            RequestOptions options = new RequestOptions().placeholder(R.drawable.loading)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
            Glide.with(context).load((String) path).apply(options).into(imageView);
        }


    }
    public void requestNew() {
        page++;
        // 根据返回到的 URL 链接进行申请和返回数据
        String address ="http://2324k8108v.iok.la:22557/news/getlist?page=5&type=comic&start="+page;    // key

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] b = response.body().bytes();



                String responseText = new String(b, "UTF-8");
                java.util.List<GsonNews> newslist = Utility.parseJsonWithGson1(responseText);




                for (GsonNews gsonnews : newslist) {
                    News news = new News(gsonnews.id, gsonnews.type,gsonnews.title,gsonnews.font,gsonnews.date);

                    List.add(0,news);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.smoothScrollToPosition(0);
                    }

                    ;
                });
            }


        });




    }
    public void refresh() {
        page=1;
        // 根据返回到的 URL 链接进行申请和返回数据
        String address = "http://2324k8108v.iok.la:22557/news/getlist?page=5&type=comic&start="+page;    // key

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "新闻加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] b = response.body().bytes();
                List.clear();
                String responseText = new String(b, "UTF-8");
                List<GsonNews> newslist = Utility.parseJsonWithGson1(responseText);




                for (GsonNews gsonnews : newslist) {
                    News news = new News(gsonnews.id, gsonnews.type,gsonnews.title,gsonnews.font,gsonnews.date);

                    List.add(news);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.smoothScrollToPosition(0);
                        //      refreshLayout.setRefreshing(false);
                    }

                    ;
                });
            }

        });

    }


    public void pd(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://2324k8108v.iok.la:22557/news/getlist?page=4&type=comicbanner&start=1").build();
                    Response response=client.newCall(request).execute();







                    byte[] b = response.body().bytes();



                    String responseText = new String(b, "UTF-8");

                    List<AnimeN> animenews = Utility.parseJsonWithGson2(responseText);


                    titles.clear();

                    for (AnimeN animeN : animenews) {

                        images.add(animeN.getFont());

                        titles.add(animeN.getTitle());
                    }


                    banner.setOnBannerListener(new OnBannerListener() {
                        long a;
                        @Override
                        public void OnBannerClick(int position) {
                            a=animenews.get(position).getId();

                            intent = new Intent(getActivity(), ContentView.class);



                            intent.putExtra("id",a);
                            startActivity(intent);

                        }
                    });
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            banner.setBannerTitles(titles);
                            banner.setImages(images);



                            banner.start();
                        }


                    });

                }catch (Exception e
                        ){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.smoothScrollToPosition(0);
                    }

                    ;
                });
            }
        }).start();

    }

}
