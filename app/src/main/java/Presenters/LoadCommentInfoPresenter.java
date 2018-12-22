package Presenters;

import com.example.liangzihong.viewpager.R;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import Activitys.ILoadCommentInfoActivity;
import Adapters.CommentInfo;
import Adapters.CommentInfoAdapter;
import BmobModels.BComment;
import cn.bmob.v3.BmobQuery;
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
    }


    @Override
    public void loadCommentInfo(String titleId) {
        BmobQuery<BComment> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("titleId",titleId);
        bmobQuery.findObjects(new FindListener<BComment>() {
            @Override
            public void done(List<BComment> bCommentList, BmobException e) {
                if (e == null)
                {
                    for(int i=0;i<bCommentList.size();i++)
                    {
                        BComment bComment = bCommentList.get(i);
                        String userId = bComment.getUserId();
                        String titleId = bComment.getTitleId();
                        String comment = bComment.getComment();

                        CommentInfo info = new CommentInfo();
                        info.setUserId(userId);
                        info.setComment(comment);

                        arr.add(0,info);
                    }

                    for (int i=0;i<arr.size();i++)
                    {
                        tmp.add(arr.get(i));
                        nowIndex++;
                        if (i>=3)
                            break;
                    }
                    adapter = new CommentInfoAdapter(iActivity.getContext(), R.layout.comment_list_item,arr);
                    iActivity.setCommentInfoAdapter(adapter);

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

        CommentInfo info = new CommentInfo();
        info.setUserId(userId);
        info.setComment(comment);

        arr.add(0,info);
        adapter.notifyDataSetChanged();
    }
}
