package Presenters;

import java.util.List;

import Adapters.CommentInfo;
import BmobModels.BComment;

/**
 * Created by Liang Zihong on 2018/12/22.
 */

public interface ILoadCommentInfoPresenter {
    public void loadCommentInfo(String titleId);

    public List<CommentInfo> getCommentInfoList();

    public void loadMore();

    public void addOnebComment(BComment bComment);
}
