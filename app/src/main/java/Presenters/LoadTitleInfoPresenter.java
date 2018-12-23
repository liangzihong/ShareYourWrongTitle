package Presenters;

import android.util.Log;

import com.example.liangzihong.viewpager.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.TitleInfo;
import Adapters.TitleInfoAdapter;
import BmobModels.BWrongTitle;
import Fragments.ILoadTitleInfoFragment;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Liang Zihong on 2018/12/21.
 */

public class LoadTitleInfoPresenter implements ILoadTitleInfoPresenter {
    private ILoadTitleInfoFragment iFragment;
    private int nowIndex=0;
    private TitleInfoAdapter adapter;
    final private List<TitleInfo> arr = new ArrayList<>();
    final private List<TitleInfo> tmp = new ArrayList<>();

    public LoadTitleInfoPresenter(ILoadTitleInfoFragment fragment){
        Bmob.initialize(fragment.getContext(), "68d5baca3da4447b7be957110d9627f3");
        iFragment = fragment;
    }

    @Override
    public void loadTitleInfo() {

        Log.e("fuck", "done: 这里是loadTitleInfoPresenter" );
        adapter = new TitleInfoAdapter(iFragment.getContext(), R.layout.used_by_fragment1_layout,tmp);
        iFragment.setTitleInfoAdapter(adapter);

        BmobQuery<BWrongTitle> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<BWrongTitle>() {
            @Override
            public void done(List<BWrongTitle> bWrongTitleList, BmobException e) {
                if (e == null)
                {
                    for(int i=0;i<bWrongTitleList.size();i++)
                    {
                        BWrongTitle wt = bWrongTitleList.get(i);
                        String userId = wt.getUserId();
                        String tag = wt.getTag();
                        String content = wt.getContent();
                        String photoUrl = wt.getPhoto().getUrl();

                        TitleInfo info = new TitleInfo();
                        info.setUserId(userId);
                        info.setPhotoUrl(photoUrl);
                        info.setTag(tag);
                        info.setContent(content);
                        info.setTitleId(wt.getObjectId());
                        arr.add(0,info);
                    }

                    for (int i=0;i<arr.size();i++)
                    {
                        tmp.add(arr.get(i));
                        nowIndex++;
                        if (i>=3)
                            break;
                    }
                    adapter.notifyDataSetChanged();

                }
                else   // 查找错题失败
                {

                }
            }
        });
    }

    @Override
    public List<TitleInfo> getTitleInfoList() {
        return arr;
    }

    // 加载更多的 数据，从 arr逐个逐个加上去。
    @Override
    public void loadMore()
    {
        if(nowIndex>arr.size())
            return;
        else {
            int cnt=0;
            for (int i=nowIndex;i<arr.size();i++)
            {
                cnt++;
                nowIndex++;
                tmp.add(arr.get(i));
                if (cnt==1)
                    break;
            }
            adapter.notifyDataSetChanged();
        }
    }


}














