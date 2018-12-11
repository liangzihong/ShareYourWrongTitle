package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.liangzihong.viewpager.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapters.Group;
import Presenters.ILoadGroupPresenter;
import Presenters.LoadGroupPresenter;

/**
 * Created by Liang Zihong on 2018/2/8.
 */

public class Fragment1 extends Fragment implements ILoadGroupFragment,AdapterView.OnItemClickListener{

    private ListView listView;
    private ILoadGroupPresenter iLoadGroupPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1_layout,container,false);
        listView=(ListView) view.findViewById(R.id.fg1_listview);
        init();
        return view;
    }

    private void init(){
        iLoadGroupPresenter=new LoadGroupPresenter(this);
        iLoadGroupPresenter.loadGroup();
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<Group> groupList = (ArrayList<Group>) iLoadGroupPresenter.getGroupList();
        Group chosenGroup = groupList.get(position);
        Toast.makeText(getContext(), chosenGroup.getProfilePhoto(), Toast.LENGTH_SHORT).show();

    }


    /**
     * 下面是需要实现接口的方法
     * @return
     */
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void setAdapter(ArrayAdapter<Group> adapter) {
        listView.setAdapter(adapter);
    }

}















