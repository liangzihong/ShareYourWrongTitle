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

        // 从数据库中查找姓名，并且加载到名字中
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("objectId", titleInfo.getUserId());
        query.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> list, BmobException e) {
                if (e==null){
                    String username = list.get(0).getUsername();
                    titleInfo.setName(username);
                    viewHolder.name.setText(titleInfo.getName());
                }
                else{
                }
            }
        });

        // 从数据库中查找头像，并且加载到头像图片中
        BmobQuery<BProfilePhoto> query2 = new BmobQuery<BProfilePhoto>();
        query2.addWhereEqualTo("userId", titleInfo.getUserId());
        query2.findObjects(new FindListener<BProfilePhoto>() {
            @Override
            public void done(List<BProfilePhoto> list, BmobException e) {
                if (e==null){
                    BmobFile bFile = list.get(0).getProfilePhotoFile();
                    String url = bFile.getUrl();
                    titleInfo.setProfileUrl(url);
                    Glide.with(smallContext).load(titleInfo.getProfileUrl()).into(viewHolder.profile);

                }
            }
        });

        viewHolder.tag.setText("标签:"+titleInfo.getTag());
        viewHolder.content.setText(titleInfo.getContent());
        Glide.with(smallContext).load(titleInfo.getPhotoUrl()).into(viewHolder.photo);


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
