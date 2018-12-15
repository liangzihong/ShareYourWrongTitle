package Presenters;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.liangzihong.viewpager.R;

import java.io.File;
import MyUtils.util1;
import Activitys.ISignupView;
import Application.MyApplication;
import BmobModels.BProfilePhoto;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import jp.wasabeef.glide.transformations.internal.Utils;

/**
 * Created by Liang Zihong on 2018/3/3.
 */

public class SignupPresenter implements ISignupPresenter {
    private ISignupView iSignupView;


    public SignupPresenter(ISignupView view) {
        iSignupView = view;
    }


    // 成功注册后，还要给 头像表赋值
    private void successSignup(final String tmp_name) {

        // 从drawable变成file需要经过 drawable -> bitmap -> file的过程，所以在util中解决，然后上传到服务器
        File file =util1.drawableToFile(MyApplication.getContext(), R.drawable.unknown_profile, "unknowprofile.png");
        final BmobFile bFile = new BmobFile(file);
//        Log.e("fuck", "successSignup:文件名字为"+ file.getAbsolutePath() );
        BProfilePhoto bProfilephoto = new BProfilePhoto();
        bProfilephoto.setUserName(tmp_name);
        bProfilephoto.setProfilePhotoFile(bFile);

        bFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    BProfilePhoto bProfilephoto = new BProfilePhoto();
                    bProfilephoto.setUserName(tmp_name);
                    bProfilephoto.setProfilePhotoFile(bFile);
                    bProfilephoto.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                        if (e==null) {
                            Toast.makeText(MyApplication.getContext(), "初始化头像成功", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MyApplication.getContext(), "初始化头像失败", Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
//                    bmobFile.getFileUrl()--返回的上传文件的完整地址
//                    toast("上传文件成功:" + bmobFile.getFileUrl());
                }else{
                    Toast.makeText(MyApplication.getContext(), "初始化头像时上传图片失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });


        iSignupView.successSignup();
    }

    private void failedSignup() {
        iSignupView.failSignup();
    }


    @Override
    public void startSignup(String name, String password) {


        // 添加 bmob 注册功能， 抛弃普通 sharepreference
        if (name.equals("") || password.equals(""))
            failedSignup();
        else {
            final String tmp_name = name;
            BmobUser bmobUser = new BmobUser();
            bmobUser.setUsername(name);
            bmobUser.setPassword(password);
            bmobUser.signUp(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser user, BmobException e) {
                    if (e == null)
                        successSignup(tmp_name);
                    else {
                        Log.e("fuck", "signup done: " + e.toString());
                        failedSignup();
                    }

                }
            });
        }

    }
}
