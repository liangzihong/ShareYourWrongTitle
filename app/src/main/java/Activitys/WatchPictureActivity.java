package Activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.liangzihong.viewpager.R;

/**
 * Created by Liang Zihong on 2018/12/11.
 */

public class WatchPictureActivity extends BaseActivity implements View.OnClickListener{

    private ImageView image;
    // 本地相册或者拍照
    public static void startPictureActivity(Context context, String StringOfUrlOfPic){
        Intent intent=new Intent(context,WatchPictureActivity.class);
        intent.putExtra("StringOfUrl",StringOfUrlOfPic);
        intent.putExtra("way","uri");
        context.startActivity(intent);
    }

    // 数据库中拿网址
    public static void startPictureActivityByInternet(Context context, String url) {
        Intent intent=new Intent(context,WatchPictureActivity.class);
        intent.putExtra("StringOfUrl",url);
        intent.putExtra("way","url");
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watchpictureview_layout);
        image=(ImageView)findViewById(R.id.PictureWatcher);
        image.setOnClickListener(this);


        Intent intent=getIntent();
        String way = intent.getStringExtra("way");
        switch (way){
            case "uri":
                String StringOfUrlOfPic=intent.getStringExtra("StringOfUrl");
                Uri uri = Uri.parse(StringOfUrlOfPic);
                image.setImageURI(uri);
                break;
            case "url":
                String url = intent.getStringExtra("StringOfUrl");
                Glide.with(this).load(url).into(image);
                break;
        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.PictureWatcher:
                finish();
        }
    }
}
