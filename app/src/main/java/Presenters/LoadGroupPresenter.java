package Presenters;

import android.content.Context;

import com.example.liangzihong.viewpager.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.Group;
import Adapters.GroupAdapter;
import Fragments.ILoadGroupFragment;

/**
 * Created by Liang Zihong on 2018/3/6.
 */

public class LoadGroupPresenter implements ILoadGroupPresenter {
    private ILoadGroupFragment iFragment;
    private ArrayList<Group> GroupList;

    public LoadGroupPresenter(ILoadGroupFragment fragment){
        iFragment=fragment;
        GroupList=new ArrayList<>();
    }


    @Override
    public void loadGroup( String[] GroupNames, int[] ProfilePhotos, String[] LastRecords) {

        //初始化 groups,ArrayList<Group>

        int size=GroupNames.length;
        for(int i=0;i<size;i++) {
            Group tmp = new Group(GroupNames[i], ProfilePhotos[i], LastRecords[i]);
            GroupList.add(tmp);
        }

        GroupAdapter adapter=new GroupAdapter(iFragment.getContext(), R.layout.used_by_fragment1_layout,GroupList);
        iFragment.setAdapter(adapter);
    }


    @Override
    public void loadGroup(){
        String []GroupNames=new String[10];
        int[]ProfilePhotos={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,
                R.drawable.pic7,R.drawable.pic8,R.drawable.pic2,R.drawable.pic1};
        String[]LastRecords=new String[10];

        for(int i=0;i<10;i++){
            GroupNames[i]="作者：";
            LastRecords[i]="标签：";
        }

        loadGroup(GroupNames,ProfilePhotos,LastRecords);

    }

    @Override
    public List<Group> getGroupList() {
        return GroupList;
    }
}
