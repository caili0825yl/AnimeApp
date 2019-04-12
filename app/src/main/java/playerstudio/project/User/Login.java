package playerstudio.project.User;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import playerstudio.project.Main;
import playerstudio.project.Mytoast;
import playerstudio.project.R;

public class Login extends AppCompatActivity {
    private Button login,register,back;
    private EditText username,password;
    private final static int LOGIN_JUDGE = 1;
    private int RequestCode = 1;
   public static String user;
public static boolean isLogin;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case LOGIN_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (result.equals("success")) {
                            Mytoast.showToast(Login.this,"登录成功！");

                            Intent intent =new Intent(Login.this,Main.class);
                            isLogin=true;
                           user=username.getText().toString();
                               startActivity(intent);
                        }
                        else{
                            Mytoast.showToast(Login.this,"用户名或密码错误！请重新输入！");

                        }

                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
            }
        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);
        login = (Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register) ;
        back=(Button)findViewById(R.id.loginback) ;
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);



register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Login.this,Register.class);
        startActivity(intent);

    }
});




back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});



login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                        String result = GetLoginData.post(username.getText().toString(), password.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("result", result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = LOGIN_JUDGE;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}
