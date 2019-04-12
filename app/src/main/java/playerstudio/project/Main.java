package playerstudio.project;


import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import playerstudio.project.List.ListMain;
import playerstudio.project.News.NewsMain;
import playerstudio.project.User.User;

public class Main extends AppCompatActivity {
    MyViewPager viewPager;
    BottomNavigationViewEx navigationViewEx;
    MyAdapter adapter;

    private BottomNavigationViewEx.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationViewEx.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_news:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.item_list:
                    viewPager.setCurrentItem(1);
                    return true;
                 /*   case R.id.item_daily:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.item_talk:
                    viewPager.setCurrentItem(3);
                    return true;
                    */
                case R.id.item_user:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    viewPager=(MyViewPager) findViewById(R.id.vp);

        viewPager.setOffscreenPageLimit(3);
        navigationViewEx=(BottomNavigationViewEx)findViewById(R.id.navigation) ;
        navigationViewEx.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigationViewEx.enableAnimation(false);
        navigationViewEx.enableShiftingMode(false);


        adapter = new MyAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsMain());
        adapter.addFragment(new ListMain());
     //   adapter.addFragment(new Daily());
      //  adapter.addFragment(new Talk());
        adapter.addFragment(new User());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        navigationViewEx.getMenu().getItem(i).setChecked(true);

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
});
    }


}
