package playerstudio.project.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import playerstudio.project.Mytoast;
import playerstudio.project.R;

public class Register extends AppCompatActivity  {
    private EditText username,password,password2;
    private Button check,back;
private String regex1 = ".*[a-z].*";
private String regex2="[a-z0-9]+";
    private final static int REGISTER_JUDGE = 2;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case REGISTER_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (result.equals("success")) {
                            Intent intent = new Intent();
                            intent.putExtra("username",username.getText().toString());
                            intent.putExtra("password",password.getText().toString());
                            Mytoast.showToast(Register.this,"注册成功！请登录");
                            finish();

                        }else {
                            Mytoast.showToast(Register.this,"该用户名已被注册！");


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
        setContentView(R.layout.register);
        username=(EditText)findViewById(R.id.rname);
        password=(EditText)findViewById(R.id.rpassword1);
        password2=(EditText)findViewById(R.id.rpassword2);
        check=(Button)findViewById(R.id.check);
        back=(Button)findViewById(R.id.back);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ! password.getText().toString().equals(password2.getText().toString())){
                    Mytoast.showToast(Register.this,"两次密码不一致！");
                }else if (!(username.getText().toString().matches(regex1))){
                   Mytoast.showToast(Register.this,"用户名不含有字母！");


                }else if(!(username.getText().toString().matches(regex2))||! password.getText().toString().matches(regex2)){
                    Mytoast.showToast(Register.this,"用户名或密码含有非法字符！");

                }
                else if(username.getText().toString().length()<8){
                   Mytoast.showToast(Register.this,"用户名过短！");
                }
                else if (password.getText().toString().length()<6){
                    Mytoast.showToast(Register.this,"密码过短！");



                }


else
                    {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = GetRegisterData.post(username.getText().toString(),
                                    password.getText().toString());
                            Bundle bundle = new Bundle();
                            bundle.putString("result",result);
                            Message msg = new Message();
                            msg.what = REGISTER_JUDGE;
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }).start();

                    }
                };



            });
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();



    }
});





;

}
}