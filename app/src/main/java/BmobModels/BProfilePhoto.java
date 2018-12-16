package BmobModels;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Liang Zihong on 2018/12/15.
 */

// BmobFile 对象是由java的File对象组成，所以不怕
public class BProfilePhoto extends BmobObject {
    private BmobFile profilePhotoFile;
    private String userId;

    public BmobFile getProfilePhotoFile() {
        return profilePhotoFile;
    }

    public void setProfilePhotoFile(BmobFile profilePhotoFile) {
        this.profilePhotoFile = profilePhotoFile;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
