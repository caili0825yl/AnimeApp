package playerstudio.project.User;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import playerstudio.project.List.AnimeAdapter;
import playerstudio.project.Main;
import playerstudio.project.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class User extends Fragment  {
private ImageView user;
private RecyclerView userList;
private List<UserList> userlist=new ArrayList<>();
    private Button login,favourite;



    public User() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user, container, false);
        login=(Button)view.findViewById(R.id.loginbt);
        user = (ImageView) view.findViewById(R.id.UserImage);
        Glide.with(this).load(R.drawable.notlogin).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(user);

        favourite=(Button)view.findViewById(R.id.favourite);
        boolean isLogin=Login.isLogin;
        String username=getActivity().getIntent().getStringExtra("username");

        if(isLogin==true){
            login.setVisibility(View.GONE);
            favourite.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.login).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(user);

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(view.getContext(),Login.class);

                startActivity(intent);
            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(view.getContext(),Favourite.class);
                intent.putExtra("username",username);
                AnimeAdapter.result=false;
                startActivity(intent);
            }
        });
        return view;

    }


    }




