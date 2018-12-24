package Activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.liangzihong.viewpager.R;

import Adapters.CommentInfo;
import Adapters.TitleInfo;
import Application.MyApplication;
import BmobModels.BComment;
import BmobModels.BWrongTitle;
import MyUi.MyListView;
import Presenters.ILoadCommentInfoPresenter;
import Presenters.LoadCommentInfoPresenter;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Liang Zihong on 2018/12/22.
 */

public class CommentPageActivity extends BaseActivity implements ILoadCommentInfoActivity {

    private ILoadCommentInfoPresenter iLoadCommentInfoPresenter;

    private  TitleInfo titleInfo;
    private ImageView photo;
    private TextView content;
    private MyListView myListView;
    private EditText write_edit;
    private Button send_button;



    public static void startCommentPageActivity(Context context, TitleInfo aTitleInfo)
    {
        Intent intent=new Intent(context, CommentPageActivity.class);
        intent.putExtra("titleId", aTitleInfo.getTitleId());
        intent.putExtra("profileUrl", aTitleInfo.getProfileUrl());
        intent.putExtra("name", aTitleInfo.getName());
        intent.putExtra("tag", aTitleInfo.getTag());
        intent.putExtra("content", aTitleInfo.getContent());
        intent.putExtra("photoUrl", aTitleInfo.getPhotoUrl());
        intent.putExtra("userId", aTitleInfo.getUserId());
        context.startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e("fuck", "oncreate: 这里是 CommentPageActivity生成");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_page);

        Bmob.initialize(this, "4bb218fd080af2496d95acc60e212704");

        Intent intent = getIntent();
        titleInfo = new TitleInfo();
        titleInfo.setTitleId(intent.getStringExtra("titleId"));
        titleInfo.setProfileUrl(intent.getStringExtra("profileUrl"));
        titleInfo.setName(intent.getStringExtra("name"));
        titleInfo.setTag(intent.getStringExtra("tag"));
        titleInfo.setContent(intent.getStringExtra("content"));
        titleInfo.setPhotoUrl(intent.getStringExtra("photoUrl"));
        titleInfo.setUserId(intent.getStringExtra("userId"));



        photo = (ImageView) findViewById(R.id.comment_page_photo);
        content = (TextView) findViewById(R.id.comment_page_content);
        myListView = (MyListView)findViewById(R.id.comment_page_MyListView);
        write_edit = (EditText)findViewById(R.id.comment_page_editText);
        send_button = (Button)findViewById(R.id.comment_page_send_button);


        init();

        iLoadCommentInfoPresenter = new LoadCommentInfoPresenter(this);
        iLoadCommentInfoPresenter.loadCommentInfo(titleInfo.getTitleId());
        // 获取评论列表，然后加载适配器。

    }


    private void init() {
        Log.e("fuck", "init: 这里是 CommentPageActivity的init");


        Glide.with(this).load(titleInfo.getPhotoUrl()).into(photo);
        content.setText(titleInfo.getContent());


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WatchPictureActivity.startPictureActivityByInternet(CommentPageActivity.this, titleInfo.getPhotoUrl());
            }
        });



        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(CommentPageActivity.this);
                normalDialog.setMessage("确定要发表评论吗");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendComment();
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                // 显示
                normalDialog.show();
            }
        });








    }

    // 发表评论
    private void sendComment()
    {

        final AlertDialog.Builder waitingDialogBuilder =
                new AlertDialog.Builder(this);
        waitingDialogBuilder.setTitle("正在发表评论");
        waitingDialogBuilder.setMessage("请稍候");
        final AlertDialog waitingDialog = waitingDialogBuilder.create();
        waitingDialog.show();

//        MyApplication app = (MyApplication)getApplication();

        String CurrentUserId = MyApplication.CurrentUserId;

        final BComment bComment = new BComment();
        bComment.setUserId(CurrentUserId);
        bComment.setTitleId(titleInfo.getTitleId());
        bComment.setComment( write_edit.getText()+"");
        bComment.setUserName(MyApplication.CurrentUserName);
        bComment.setProfileUrl(MyApplication.CurrentProfileUrl);
        bComment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null)
                {
                    Log.e("fuck", "done: 成功" );
                    waitingDialog.dismiss();
                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(CommentPageActivity.this);
                    normalDialog.setTitle("系统信息");
                    normalDialog.setMessage("成功发布评论");

                    write_edit.setText("");
                    normalDialog.show();
                    iLoadCommentInfoPresenter.addOnebComment(bComment);
                }
                else
                {
                    Log.e("fuck", "done: 失败" );
                    Log.e("fuck", "发布评论失败,用户Id为"+bComment.getUserId() );

                    waitingDialog.dismiss();

                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(CommentPageActivity.this);
                    normalDialog.setTitle("系统信息");
                    normalDialog.setMessage("发布评论失败\n原因："+e.toString());
                    normalDialog.show();
                }
            }
        });

    }


    // 加载评论列表的接口
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setCommentInfoAdapter(ArrayAdapter<CommentInfo> adapter) {
        myListView.setAdapter(adapter);
    }
}
