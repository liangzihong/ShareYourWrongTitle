package BmobModels;

import cn.bmob.v3.BmobObject;

/**
 * Created by Liang Zihong on 2018/12/23.
 */

public class BComment extends BmobObject {
    private String titleId;
    private String userId;
    private String comment;

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
