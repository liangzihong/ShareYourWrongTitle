package Presenters;

import android.util.Log;

import com.example.liangzihong.viewpager.R;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import Activitys.ILoadCommentInfoActivity;
import Adapters.CommentInfo;
import Adapters.CommentInfoAdapter;
import BmobModels.BComment;
import BmobModels.BProfilePhoto;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Liang Zihong on 2018/12/22.
 */

public class LoadCommentInfoPresenter implements ILoadCommentInfoPresenter {

    private ILoadCommentInfoActivity iActivity;
    private int nowIndex=0;
    private CommentInfoAdapter adapter;
    final private List<CommentInfo> arr = new ArrayList<>();
    final private List<CommentInfo> tmp = new ArrayList<>();

    public LoadCommentInfoPresenter(ILoadCommentInfoActivity activity)
    {
        iActivity = activity;
        Bmob.initialize(activity.getContext(), "68d5baca3da4447b7be957110d9627f3");
    }


    @Override
    public void loadCommentInfo(String titleId) {

        adapter = new CommentInfoAdapter(iActivity.getContext(), R.layout.comment_list_item,tmp);
        iActivity.setCommentInfoAdapter(adapter);

        Log.e("fuck", "loadCommentInfo: 这里是loadCommentInfoPresenter" );
        BmobQuery<BComment> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("titleId",titleId);
        bmobQuery.findObjects(new FindListener<BComment>() {
            @Override
            public void done(List<BComment> bCommentList, BmobException e) {
                if (e == null)
                {
                    Log.e("fuck", "loadCommentInfo: 这里是loadCommentInfoPresenter 查找评论成功" );
                    for(int i=0;i<bCommentList.size();i++)
                    {
                        try {
                            Thread.sleep( 500 );
                        } catch (Exception ee){
                            System.exit( 0 ); //退出程序
                        }
                        BComment bComment = bCommentList.get(i);
                        String userId = bComment.getUserId();
                        String titleId = bComment.getTitleId();
                        String comment = bComment.getComment();

                        final CommentInfo info = new CommentInfo();
                        info.setUserId(userId);
                        info.setComment(comment);

                        // 先找姓名
                        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
                        query.addWhereEqualTo("objectId", info.getUserId());
                        query.findObjects(new FindListener<BmobUser>() {
                            @Override
                            public void done(List<BmobUser> list, BmobException e) {
                                if (e == null) {
                                    String username = list.get(0).getUsername();
                                    info.setName(username);
                                }

                                // 再找头像
                                BmobQuery<BProfilePhoto> query2 = new BmobQuery<BProfilePhoto>();
                                query2.addWhereEqualTo("userId", info.getUserId());
                                query2.findObjects(new FindListener<BProfilePhoto>() {
                                    @Override
                                    public void done(List<BProfilePhoto> list, BmobException e) {
                                        if (e==null){
                                            BmobFile bFile = list.get(0).getProfilePhotoFile();
                                            String url = bFile.getUrl();
                                            info.setProfileUrl(url);

                                        }

                                        arr.add(0,info);
                                        tmp.add(0,info);
                                        Log.e("fuck", "加载完一条评论");
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        });
                    }


                }
                else   // 查找评论失败
                {

                }
            }
        });


    }

    @Override
    public List<CommentInfo> getCommentInfoList() {
        return arr;
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void addOnebComment(BComment bComment) {
        String userId = bComment.getUserId();
        String titleId = bComment.getTitleId();
        String comment = bComment.getComment();

        final CommentInfo info = new CommentInfo();
        info.setUserId(userId);
        info.setComment(comment);

        // 先找姓名
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("objectId", info.getUserId());
        query.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> list, BmobException e) {
                if (e == null) {
                    String username = list.get(0).getUsername();
                    info.setName(username);
                }

                // 再找头像
                BmobQuery<BProfilePhoto> query2 = new BmobQuery<BProfilePhoto>();
                query2.addWhereEqualTo("userId", info.getUserId());
                query2.findObjects(new FindListener<BProfilePhoto>() {
                    @Override
                    public void done(List<BProfilePhoto> list, BmobException e) {
                        if (e==null){
                            BmobFile bFile = list.get(0).getProfilePhotoFile();
                            String url = bFile.getUrl();
                            info.setProfileUrl(url);

                        }

                        arr.add(0,info);
                        tmp.add(0,info);
                        Log.e("fuck", "加载完一条评论");
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
