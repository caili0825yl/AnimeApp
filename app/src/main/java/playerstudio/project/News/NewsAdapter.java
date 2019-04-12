package playerstudio.project.News;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import playerstudio.project.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> newsList;
    private Context mContext;


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView newsimage;
        TextView newsname;
        View NewsView;
        public ViewHolder(View view){
            super(view);
            NewsView=view;
            newsimage=(ImageView)view.findViewById(R.id.newsimage);
            newsname=(TextView)view.findViewById(R.id.newstitle);


        }
    }

    public NewsAdapter(List<News> newsList,Context context){
        this.newsList=newsList;
        this.mContext=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item,viewGroup,false);
       ViewHolder holder=new ViewHolder(view);
        holder.NewsView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                int position=holder.getAdapterPosition();
                News news=newsList.get(position);
                Intent intent=new Intent(mContext,NewsContentView.class);
                intent.putExtra("listid",news.getId());
                mContext.startActivity(intent);

            }
        });





        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        News news=newsList.get(i);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.loading);

        Glide.with(mContext).load(newsList.get(i).getFont())
               .apply(options).into(viewHolder.newsimage);
        viewHolder.newsname.setText(news.getTitle());


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
