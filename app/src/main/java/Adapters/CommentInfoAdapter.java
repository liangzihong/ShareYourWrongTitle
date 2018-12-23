package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.liangzihong.viewpager.R;

import java.io.File;
import java.util.List;

import Activitys.CommentPageActivity;
import Activitys.WatchPictureActivity;
import BmobModels.BProfilePhoto;
import MyUtils.util1;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Liang Zihong on 2018/12/22.
 */

public class CommentInfoAdapter extends ArrayAdapter<CommentInfo> {
    private int resourceId;
    private Context smallContext;


    // 在这里得到的 TitleInfo都是没有用户名和用户头像的，所以 加载时，需要到数据库查找。
    public CommentInfoAdapter(Context context, int textViewResourceId, List<CommentInfo> list) {
        super(context, textViewResourceId, list);
        resourceId=textViewResourceId;
        smallContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final CommentInfo commentInfo = getItem(position);
        final CommentInfoAdapter.ViewHolder viewHolder;
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new CommentInfoAdapter.ViewHolder();

            viewHolder.profile=(ImageView)view.findViewById(R.id.myListItem_profile);
            viewHolder.name=(TextView)view.findViewById(R.id.myListItem_name);
            viewHolder.comment = (TextView) view.findViewById(R.id.myListItem_comment);

            view.setTag(viewHolder);
        }
        else{
            view=convertView;
            viewHolder=(CommentInfoAdapter.ViewHolder) view.getTag();
        }


//        Log.e("fuck", "getView: 这里是 CommentInfoAdapter负责加载 评论");
        viewHolder.comment.setText(commentInfo.getComment());

        viewHolder.name.setText(commentInfo.getName());

        Glide.with(smallContext).load(commentInfo.getProfileUrl()).into(viewHolder.profile);




        return view;
    }

    class ViewHolder
    {
        ImageView profile;
        TextView name;
        TextView comment;
    }
}
