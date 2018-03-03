package Activitys;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import Adapters.MyFragmentPagerAdapter;
import Adapters.MyViewPagerAdapter;
import com.example.liangzihong.viewpager.R;

import java.util.ArrayList;
import java.util.List;

import Fragments.Fragment1;
import Fragments.Fragment2;
import Fragments.Fragment3;
import Fragments.Fragment4;

public class MainActivity extends AppCompatActivity {

    private List<View> viewList;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        viewPager=(ViewPager)findViewById(R.id.viewpager);

        //第一种方式：利用view和List<view> 和继承  PagerAdapter的adapter

        //第二种方式，利用Fragment类来将layout文件实例化成view，和 继承 FragmentPagerAdapter的adapter

        secondMethod();
        radioGroupInit();



    }

    /**
     * 第一种方式
     */
    private void firstMethod(){
        viewList=new ArrayList<>();

        /**
         * 从layout文件中变为view
         */
        View view1=View.inflate(this,R.layout.view1,null);
        View view2=View.inflate(this,R.layout.view2,null);
        View view3=View.inflate(this,R.layout.view3,null);
        View view4=View.inflate(this,R.layout.view4,null);

        /**
         * 数据源
         */
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        /**
         * 适配器初始化
         */
        MyViewPagerAdapter adapter=new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
    }


    /**
     * 第二种方式
     */
    private void secondMethod(){

        fragmentList=new ArrayList<>();

        Fragment1 fragment1=new Fragment1();
        Fragment2 fragment2=new Fragment2();
        Fragment3 fragment3=new Fragment3();
        Fragment4 fragment4=new Fragment4();

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);

        MyFragmentPagerAdapter adapter2=new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter2);


    }

    /**
     * radioGroup的初始化以及监听器的设置
     */
    private void radioGroupInit(){
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch(checkedId){
                    case R.id.first:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.second:
                        viewPager.setCurrentItem(1);
                        break;

                    case R.id.third:
                        viewPager.setCurrentItem(2);
                        break;

                    case R.id.forth:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
    }
}


















