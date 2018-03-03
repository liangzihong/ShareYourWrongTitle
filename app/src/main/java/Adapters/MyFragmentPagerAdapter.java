package Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Liang Zihong on 2018/2/8.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    /**
     * 返回具体的页卡
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    /**
     * 返回页卡的个数
     * @return
     */
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
