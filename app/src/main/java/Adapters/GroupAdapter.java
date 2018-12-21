package Adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangzihong.viewpager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Activitys.WatchPictureActivity;
import Application.MyApplication;
import MyUtils.util1;
/**
 * Created by Liang Zihong on 2018/3/6.
 */

public class GroupAdapter extends ArrayAdapter<Group> {

    private int resourceId;
    private Context smallContext;

    public GroupAdapter(Context context, int textViewResourceId, List<Group> list) {
        super(context, textViewResourceId, list);
        resourceId=textViewResourceId;
        smallContext = context;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        Group group=getItem(position);
        ViewHolder viewHolder;
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.profilephoto=(ImageView)view.findViewById(R.id.image);
            viewHolder.lastrecord=(TextView)view.findViewById(R.id.Title_Tags);
            viewHolder.name=(TextView)view.findViewById(R.id.Author_Name);
            view.setTag(viewHolder);
        }
        else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        viewHolder.profilephoto.setImageResource(group.getProfilePhoto());
        viewHolder.name.setText(group.getGroupName());
        viewHolder.lastrecord.setText(group.getLastRecord());


        // 打开照片应该如何打开。
        viewHolder.profilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInternet = false; // 如果还没写网络
                if (isInternet == false ) {
                    File tmp_file = util1.drawableToFile(smallContext, R.drawable.sample, "sample.png");
                    WatchPictureActivity.startPictureActivity(smallContext ,tmp_file.getPath() );
                }
                else {
                    // 如果有网络，就写网络
                    // 则直接url访问。
                }
            }
        });


        return view;
    }

    class ViewHolder
    {
        ImageView profilephoto;
        TextView name;
        TextView lastrecord;
    }

}



















