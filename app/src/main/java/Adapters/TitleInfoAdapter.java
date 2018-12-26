package Adapters;

import android.content.Context;
import android.media.Image;
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
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Liang Zihong on 2018/12/21.
 */

public class TitleInfoAdapter extends ArrayAdapter<TitleInfo> {
    private int resourceId;
    private Context smallContext;


    // 在这里得到的 TitleInfo都是没有用户名和用户头像的，所以 加载时，需要到数据库查找。
    public TitleInfoAdapter(Context context, int textViewResourceId, List<TitleInfo> list) {
        super(context, textViewResourceId, list);
        resourceId=textViewResourceId;
        smallContext = context;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.e("fuck", "notifyDataSetChanged: 这里是titleInfoAdapter" );
        Log.e("fuck", "titleInfoAdapter的线程号"+ android.os.Process.myPid() );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final TitleInfo titleInfo=getItem(position);
        final TitleInfoAdapter.ViewHolder viewHolder;
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new TitleInfoAdapter.ViewHolder();

            viewHolder.profile=(ImageView)view.findViewById(R.id.fragment1_profile);
            viewHolder.tag=(TextView)view.findViewById(R.id.Title_Tags);
            viewHolder.name=(TextView)view.findViewById(R.id.Author_Name);
            viewHolder.content = (TextView)view.findViewById(R.id.fragment1_content);
            viewHolder.photo=(ImageView)view.findViewById(R.id.image);
            viewHolder.comment = (ImageView)view.findViewById(R.id.fragment1_comment);

            view.setTag(viewHolder);
        }
        else{
            view=convertView;
            viewHolder=(TitleInfoAdapter.ViewHolder) view.getTag();
        }




        viewHolder.name.setText(titleInfo.getName());
        viewHolder.tag.setText(titleInfo.getTag());
        viewHolder.content.setText(titleInfo.getContent());
        if (titleInfo.getPhotoUrl()!=null)
            Glide.with(smallContext).load(titleInfo.getPhotoUrl()).into(viewHolder.photo);
        Glide.with(smallContext).load(titleInfo.getProfileUrl()).into(viewHolder.profile);






        // 打开照片应该如何打开。
        viewHolder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInternet = true; // 如果还没写网络
                if (isInternet == false ) {
                    File tmp_file = util1.drawableToFile(smallContext, R.drawable.sample, "sample.png");
                    WatchPictureActivity.startPictureActivity(smallContext ,tmp_file.getPath() );
                }
                else {
                    // 如果有网络，就写网络
                    // 则直接url访问。
                    WatchPictureActivity.startPictureActivityByInternet(smallContext, titleInfo.getPhotoUrl());
                }
            }
        });

        // 进入评论页面
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentPageActivity.startCommentPageActivity(smallContext, titleInfo);
            }
        });


        return view;
    }

    class ViewHolder
    {
        ImageView profile;
        TextView tag;
        TextView name;
        TextView content;
        ImageView photo;
        ImageView comment;
    }

}
