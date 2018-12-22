package Activitys;

import android.content.Context;
import android.widget.ArrayAdapter;

import Adapters.CommentInfo;

/**
 * Created by Liang Zihong on 2018/12/22.
 */

public interface ILoadCommentInfoActivity {
    public Context getContext();
    public void setCommentInfoAdapter(ArrayAdapter<CommentInfo> adapter);
}
