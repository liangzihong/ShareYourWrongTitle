package Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.liangzihong.viewpager.R;

import java.util.ArrayList;

import Activitys.CommentPageActivity;
import Adapters.TitleInfo;
import Application.MyApplication;
import Presenters.ILoadTitleInfoPresenter;
import Presenters.LoadTitleInfoPresenter;
import cn.bmob.v3.Bmob;

import static android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS;

/**
 * Created by Liang Zihong on 2018/2/8.
 */

public class Fragment1 extends Fragment implements  ILoadTitleInfoFragment{

    private ListView listView;
    private ILoadTitleInfoPresenter iLoadTitleInfoPresenter;
    private Activity myActivity;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        Bmob.initialize(getActivity(), "68d5baca3da4447b7be957110d9627f3");
        View view=inflater.inflate(R.layout.fragment1_layout,container,false);
        listView=(ListView) view.findViewById(R.id.fg1_listview);
        myActivity = getActivity();
        init();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void init(){

        iLoadTitleInfoPresenter = new LoadTitleInfoPresenter(this);
        iLoadTitleInfoPresenter.loadTitleInfo();
//
//        // 当拉到最底时，再加载
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            //AbsListView view 这个view对象就是listview
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
//                        iLoadTitleInfoPresenter.loadMore();
////                        Toast.makeText(myActivity, "你到底部了", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//
//            }
//        });

    }





    /**
     * 下面是需要实现接口的方法
     * @return
     */
    @Override
    public Context getContext() {
        return MyApplication.getContext();
    }


    @Override
    public void setTitleInfoAdapter(ArrayAdapter<TitleInfo> adapter) {
        listView.setAdapter(adapter);
    }
}















