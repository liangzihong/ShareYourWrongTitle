package Adapters;

import android.content.Context;
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

        // 从数据库中查找姓名，并且加载到名字中
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("objectId", commentInfo.getUserId());
        query.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> list, BmobException e) {
                if (e==null){
                    String username = list.get(0).getUsername();
                    commentInfo.setName(username);
                    viewHolder.name.setText(commentInfo.getName());
                }
                else{
                }
            }
        });

        // 从数据库中查找头像，并且加载到头像图片中
        BmobQuery<BProfilePhoto> query2 = new BmobQuery<BProfilePhoto>();
        query2.addWhereEqualTo("userId", commentInfo.getUserId());
        query2.findObjects(new FindListener<BProfilePhoto>() {
            @Override
            public void done(List<BProfilePhoto> list, BmobException e) {
                if (e==null){
                    BmobFile bFile = list.get(0).getProfilePhotoFile();
                    String url = bFile.getUrl();
                    commentInfo.setProfileUrl(url);
                    Glide.with(smallContext).load(commentInfo.getProfileUrl()).into(viewHolder.profile);

                }
            }
        });

        // 评论的话
        viewHolder.comment.setText(commentInfo.getComment());

        return view;
    }

    class ViewHolder
    {
        ImageView profile;
        TextView name;
        TextView comment;
    }
}
