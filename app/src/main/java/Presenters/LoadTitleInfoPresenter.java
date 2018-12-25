package Presenters;

import android.util.Log;

import com.example.liangzihong.viewpager.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.TitleInfo;
import Adapters.TitleInfoAdapter;
import BmobModels.BProfilePhoto;
import BmobModels.BWrongTitle;
import Fragments.ILoadTitleInfoFragment;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
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


    final Integer now_cnts[] = new Integer[1];


    public LoadTitleInfoPresenter(ILoadTitleInfoFragment fragment){
        Bmob.initialize(fragment.getContext(), "4bb218fd080af2496d95acc60e212704");
        iFragment = fragment;
    }


    // 关键步骤， 之前崩溃的bug是因为线程问题。
    // onsuccess之后，其实是一个同步线程。
    // onsuccess由始至终都是 主线程！！！！！！！跟 adapter.notifychange是同一个线程！！！
    // 所以，当一个主线程在执行notifychange， 此时又有另一条错题 onsuccess，线程就会崩溃。因为a事情还没做完，b事情又追着来。

    // 比如有九条错题需要获取，之前是  拿了前三条就直接  adapter.notifychange。
    // 但是之后 如果线程在notifychange。此时 有其他其他错题来了，就崩溃了。

    // 解决方案：等待一批数据全部加载完，才 adapter.notifychange
    // 细节：因为网络速度不同，所以不能按序号，所以只能完成一个就+1，当 now_cnts[0] == tmp_list.size()时，说明全部搞定。

    @Override
    public void loadTitleInfo() {
        BmobFile xxx = new BmobFile();
        Log.e("fuck", "查询错题的线程号"+ xxx.getUrl() );

        Log.e("fuck", "主线程号为："+ android.os.Process.myPid() );
        Log.e("fuck", "done: 这里是loadTitleInfoPresenter" );
        adapter = new TitleInfoAdapter(iFragment.getContext(), R.layout.used_by_fragment1_layout,tmp);
        iFragment.setTitleInfoAdapter(adapter);

        BmobQuery<BWrongTitle> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<BWrongTitle>() {
            @Override
            public void done(List<BWrongTitle> bWrongTitleList, BmobException e) {
                if (e == null)
                {
                    Log.e("fuck", "查询错题的线程号"+ android.os.Process.myPid() );
                    final List<TitleInfo> tmp_list = new ArrayList<TitleInfo>();
                    for(int i=0;i<bWrongTitleList.size();i++)
                    {
                        BWrongTitle wt = bWrongTitleList.get(i);
                        String userId = wt.getUserId();
                        String tag = wt.getTag();
                        String content = wt.getContent();
                        String photoUrl = null;
                        if( wt.getPhoto()!=null)
                             photoUrl = wt.getPhoto().getUrl();
                        String userName = wt.getUserName();
                        String userProfileUrl = wt.getProfileUrl();

                        final TitleInfo info = new TitleInfo();
                        info.setUserId(userId);
                        info.setPhotoUrl(photoUrl);
                        info.setTag(tag);
                        info.setContent(content);
                        info.setTitleId(wt.getObjectId());
                        info.setProfileUrl(userProfileUrl);
                        info.setName(userName);
                        tmp_list.add(info);

                        arr.add(0,info);
                        Log.e("fuck", "什么鬼" );
                    }

                    for(int i=0;i<arr.size();i++)
                    {
                        nowIndex++;
                        tmp.add(arr.get(i));
                        if (tmp.size()>=3)
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
                nowIndex++;
                tmp.add(arr.get(i));
                if (cnt==1)
                    break;
            }
            adapter.notifyDataSetChanged();
        }
    }





}














