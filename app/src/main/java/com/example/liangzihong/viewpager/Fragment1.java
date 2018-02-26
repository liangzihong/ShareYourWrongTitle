package com.example.liangzihong.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang Zihong on 2018/2/8.
 */

public class Fragment1 extends Fragment {
    private SimpleAdapter adapter;
    private List<Map<String,Object>> dataList;
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1_layout,container,false);
        listView=(ListView) view.findViewById(R.id.fg1_listview);
        dataList=new ArrayList<>();
        init();
        return view;
    }

    private void init(){



        /**
         * 数据源初始化
         */
        for(int i=0;i<10;i++){
            String Name="群"+i;
            String LastRecord="今晚"+i+"点来集合";
            int Picture=R.mipmap.ic_launcher_round;
            Map<String,Object> map=new HashMap<>();
            map.put("Picture",Picture);
            map.put("Name",Name);
            map.put("LastRecord",LastRecord);
            dataList.add(map);
        }


        adapter=new SimpleAdapter(getActivity(),dataList,R.layout.used_by_fragment1_layout,
                new String[]{"Picture","Name","LastRecord"},new int[]{R.id.image,R.id.Name,R.id.LastRecord});


        listView.setAdapter(adapter);

        /**
         * 设置响应事件
         */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map=(Map<String,Object>)listView.getItemAtPosition(position);
                Toast.makeText(getActivity(), map.get("Name")+"不能进行聊天", Toast.LENGTH_SHORT).show();
            }
        });
    }

}















