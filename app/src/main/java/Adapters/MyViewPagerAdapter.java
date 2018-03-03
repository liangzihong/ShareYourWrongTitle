package Adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Liang Zihong on 2018/2/8.
 */

public class MyViewPagerAdapter extends PagerAdapter   {

    private List<View> viewList;


    public MyViewPagerAdapter(List<View> viewList){
        this.viewList =viewList;
    }

    /**
     * 返回页面的个数
     * */
    @Override
    public int getCount() {
        return viewList.size();
    }


    /**
     *view是否来自于对象，官方推荐是返回view==object
     * */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 实例化一个页卡
     * */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    /**
     * 销毁一个页面
     * */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
}














