package playerstudio.project;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends FragmentPagerAdapter {
    //创建FragmentManager
    private List<Fragment> listfragment=new ArrayList<>(); //创建一个List<Fragment>
    //定义构造带两个参数
    public MyAdapter(FragmentManager fm) {
        super(fm);


    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return listfragment.get(arg0); //返回第几个fragment
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listfragment.size(); //总共有多少个fragment
    }
    public void addFragment(Fragment fragment) {
        listfragment.add(fragment);
    }
}
