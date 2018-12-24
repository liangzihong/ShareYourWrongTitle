package MyUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import Application.MyApplication;
import BmobModels.BComment;
import BmobModels.BProfilePhoto;
import BmobModels.BWrongTitle;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;

/**
 * Created by Liang Zihong on 2018/12/15.
 */

public class util1 {


    public static File drawableToFile(Context mContext, int drawableId, String fileName){

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), drawableId);
        String defaultPath = mContext.getFilesDir()
                .getAbsolutePath() + "/imgs";
        File file = new File(defaultPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String defaultImgPath = defaultPath + "/"+fileName;
        file = new File(defaultImgPath);
        try {
            if (file.exists())
                return file;
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 20, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    public static void modifyName()
    {
        String userId = MyApplication.CurrentUserId;
        final String userName = MyApplication.CurrentUserName;

        BmobQuery<BProfilePhoto> query2 = new BmobQuery<BProfilePhoto>();
        query2.addWhereEqualTo("userId", userId);
        query2.findObjects(new FindListener<BProfilePhoto>() {
            @Override
            public void done(List<BProfilePhoto> list, BmobException e) {
                if (e == null) {
                    List<BmobObject> update_list = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        BProfilePhoto bProfilePhoto = list.get(i);
                        bProfilePhoto.setUserName(userName);
                        update_list.add(bProfilePhoto);
                    }


                    new BmobBatch().updateBatch(update_list).doBatch(new QueryListListener<BatchResult>() {

                        @Override
                        public void done(List<BatchResult> results, BmobException e) {
                            if (e == null) {
                                for (int i = 0; i < results.size(); i++) {
                                    BatchResult result = results.get(i);
                                    BmobException ex = result.getError();
                                    if (ex == null) {
                                        Log.e("lost", "done: 头像表更改姓名成功");
                                    } else {
                                        Log.e("lost", "done: 头像表更改姓名失败");
                                    }
                                }
                            } else {
                                Log.e("lost", "done: 头像表更改姓名失败");
                            }
                        }
                    });
                }
            }
        });

        BmobQuery<BWrongTitle> query3 = new BmobQuery<BWrongTitle>();
        query3.addWhereEqualTo("userId", userId);
        query3.findObjects(new FindListener<BWrongTitle>() {
            @Override
            public void done(List<BWrongTitle> list, BmobException e) {
                if (e == null) {

                    List<BmobObject> update_list = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        BWrongTitle bWrongTitle = list.get(i);
                        bWrongTitle.setUserName(userName);
                        update_list.add( bWrongTitle);
                    }


                    new BmobBatch().updateBatch(update_list).doBatch(new QueryListListener<BatchResult>() {

                        @Override
                        public void done(List<BatchResult> results, BmobException e) {
                            if (e == null) {
                                for (int i = 0; i < results.size(); i++) {
                                    BatchResult result = results.get(i);
                                    BmobException ex = result.getError();
                                    if (ex == null) {
                                        Log.e("lost", "done: 错题表更改姓名成功");
                                    } else {
                                        Log.e("lost", "done: 错题表更改姓名失败");
                                    }
                                }
                            } else {
                                Log.e("lost", "done: 错题表更改姓名失败");
                            }
                        }
                    });
                }
            }
        });

        BmobQuery<BComment> query4 = new BmobQuery<BComment>();
        query4.addWhereEqualTo("userId", userId);
        query4.findObjects(new FindListener<BComment>() {
            @Override
            public void done(List<BComment> list, BmobException e) {
                if (e == null) {
                    List<BmobObject> update_list = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        BComment bComment = list.get(i);
                        bComment.setUserName(userName);
                        update_list.add( bComment);
                    }


                    new BmobBatch().updateBatch(update_list).doBatch(new QueryListListener<BatchResult>() {

                        @Override
                        public void done(List<BatchResult> results, BmobException e) {
                            if (e == null) {
                                for (int i = 0; i < results.size(); i++) {
                                    BatchResult result = results.get(i);
                                    BmobException ex = result.getError();
                                    if (ex == null) {
                                        Log.e("lost", "done: 评论表更改姓名成功");
                                    } else {
                                        Log.e("lost", "done: 评论表更改姓名失败");
                                    }
                                }
                            } else {
                                Log.e("lost", "done: 评论表更改姓名失败");
                            }
                        }
                    });
                }
            }
        });




    }




    public static void modifyProfileUrl()
    {
        String userId = MyApplication.CurrentUserId;
        final String profileUrl = MyApplication.CurrentProfileUrl;



        BmobQuery<BWrongTitle> query3 = new BmobQuery<BWrongTitle>();
//        query3.addWhereEqualTo("userId", userId);
        Log.e("lost", "userId: "+userId );
        query3.findObjects(new FindListener<BWrongTitle>() {
            @Override
            public void done(List<BWrongTitle> list, BmobException e) {

                if(e==null) {
                    List<BmobObject> update_list = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        BWrongTitle bWrongTitle = list.get(i);
                        bWrongTitle.setProfileUrl(profileUrl);
                        update_list.add( bWrongTitle);
                    }


                    new BmobBatch().updateBatch(update_list).doBatch(new QueryListListener<BatchResult>() {

                        @Override
                        public void done(List<BatchResult> results, BmobException e) {
                            if (e == null) {
                                for (int i = 0; i < results.size(); i++) {
                                    BatchResult result = results.get(i);
                                    BmobException ex = result.getError();
                                    if (ex == null) {
                                        Log.e("lost", "done: 错题表更改头像url成功");
                                    } else {
                                        Log.e("lost", "done: 错题表更改头像url失败");
                                    }
                                }
                            } else {
                                Log.e("lost", "done: 错题表更改头像url失败");
                            }
                        }
                    });
                }
                else
                {
                    Log.e("lost", "done: 错题表更改头像url失败"+e.toString());
                }
            }
        });

        BmobQuery<BComment> query4 = new BmobQuery<BComment>();
        query4.addWhereEqualTo("userId", userId);
        query4.findObjects(new FindListener<BComment>() {
            @Override
            public void done(List<BComment> list, BmobException e) {
                if (e == null) {
                    List<BmobObject> update_list = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        BComment bComment = list.get(i);
                        bComment.setProfileUrl(profileUrl);
                        update_list.add(bComment);
                    }


                    new BmobBatch().updateBatch(update_list).doBatch(new QueryListListener<BatchResult>() {

                        @Override
                        public void done(List<BatchResult> results, BmobException e) {
                            if (e == null) {
                                for (int i = 0; i < results.size(); i++) {
                                    BatchResult result = results.get(i);
                                    BmobException ex = result.getError();
                                    if (ex == null) {
                                        Log.e("lost", "done: 错题表更改头像url成功");
                                    } else {
                                        Log.e("lost", "done: 错题表更改头像url失败");
                                    }
                                }
                            } else {
                                Log.e("lost", "done: 错题表更改头像url失败");
                            }
                        }
                    });
                }
            }
        });

    }

}
