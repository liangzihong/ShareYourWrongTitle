package Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.liangzihong.viewpager.R;

import java.io.File;

import Activitys.WatchPictureActivity;
import Application.MyApplication;
import BmobModels.BProfilePhoto;
import BmobModels.BWrongTitle;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Liang Zihong on 2018/2/8.
 */

public class Fragment3 extends Fragment {
    public static final int CHOOSE_FROM_ALBUM=1;
    public static final int TAKE_PHOTO=0;
    private Uri imageUri=null;
    private File file;
    private String filePath;

    private boolean isAlbum;

    private Button album_button;
    private Button camera_button;

    private EditText editTag;
    private EditText editContent;
    private Button send_button;

    private ImageView imageView;
    private Activity myActivity;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState){

        imageUri = null;
        myActivity = this.getActivity();

        // 初始化一堆控件
        View view = inflater.inflate(R.layout.view3,container,false);
        album_button = (Button)view.findViewById(R.id.view3_album_button);
        camera_button = (Button)view.findViewById(R.id.view3_camera_button);
        imageView = (ImageView)view.findViewById(R.id.view3_imageView);

        editTag = (EditText)view.findViewById(R.id.view3_tag_edit);
        editContent = (EditText)view.findViewById(R.id.view3_content_edit);
        send_button = (Button)view.findViewById(R.id.view3_send_button);
        isAlbum = false;
        init();
        return view;
    }



    // 初始化一堆响应事件
    private void init() {
        // 相册
        album_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyPermission();
            }
        });

        // 相机
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTakingPhoto();
            }
        });

        // 照片
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null)
                    WatchPictureActivity.startPictureActivity( myActivity, imageUri.toString());
            }
        });

        // 发送错题
        // 选择框
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(myActivity);
                normalDialog.setMessage("确定要发表吗");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendWrongTitle();
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




    // 发送错题
    // 给WrongTitle赋值，然后上传即可
    private void sendWrongTitle()
    {
        final String userId = MyApplication.CurrentUserId;
//        final MyApplication app = (MyApplication) myActivity.getApplication();


        if (imageUri!=null)
        {
            // 因为如果 拍摄 和 从相册拿，两者的uri变为路径的方式不同，所以要分类讨论。
            String imagePath;
            if (isAlbum)
                imagePath = getImagePath(imageUri, null);
            else {
                imagePath = filePath;
            }
            final File photo_file = new File(imagePath);
            final BmobFile bFile = new BmobFile(photo_file);

            final AlertDialog.Builder waitingDialogBuilder =
                    new AlertDialog.Builder(myActivity);
            waitingDialogBuilder.setTitle("正在上传");
            waitingDialogBuilder.setMessage("请稍候");
            final AlertDialog waitingDialog = waitingDialogBuilder.create();
            waitingDialog.show();

            bFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        BWrongTitle wrongTitle = new BWrongTitle();
                        wrongTitle.setUserId(userId);
                        wrongTitle.setContent( editContent.getText().toString() );
                        wrongTitle.setTag( editTag.getText().toString() );
                        wrongTitle.setPhoto(bFile);
                        wrongTitle.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e==null)
                                {
                                    waitingDialog.dismiss();
                                    final AlertDialog.Builder normalDialog =
                                            new AlertDialog.Builder(myActivity);
                                    normalDialog.setTitle("系统信息");
                                    normalDialog.setMessage("成功发布错题");

                                    editContent.setText("");
                                    editTag.setText("");
                                    imageView.setImageResource(R.drawable.add_photo);
                                    normalDialog.show();
                                }
                                else
                                {
                                    waitingDialog.dismiss();

                                    final AlertDialog.Builder normalDialog =
                                            new AlertDialog.Builder(myActivity);
                                    normalDialog.setTitle("系统信息");
                                    normalDialog.setMessage("发布错题失败，请重新尝试"+e.toString());
                                    normalDialog.show();
                                }
                            }
                        });

                    }
                    else
                    {
                        final AlertDialog.Builder normalDialog =
                                new AlertDialog.Builder(myActivity);
                        normalDialog.setTitle("系统信息");
                        normalDialog.setMessage("上传图片失败，请重新尝试");
                        normalDialog.show();
                    }

                }

                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });

        }

        else {
            BWrongTitle wrongTitle = new BWrongTitle();
            wrongTitle.setUserId(userId);
            wrongTitle.setContent( editContent.getText().toString() );
            wrongTitle.setTag( editTag.getText().toString() );
            wrongTitle.setPhoto(null);
            wrongTitle.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e==null)
                    {
                        final AlertDialog.Builder normalDialog =
                                new AlertDialog.Builder(myActivity);
                        normalDialog.setTitle("系统信息");
                        normalDialog.setMessage("成功发布错题");
                        normalDialog.show();
                    }
                    else
                    {
                        final AlertDialog.Builder normalDialog =
                                new AlertDialog.Builder(myActivity);
                        normalDialog.setTitle("系统信息");
                        normalDialog.setMessage("发布错题失败，请重新尝试");
                        normalDialog.show();
                    }
                }
            });
        }
    }





































// 拍照看相册专用--------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 申请权限
     */
    private void applyPermission(){
        //如果没有得到 读写sd卡的权限
        if(ContextCompat.checkSelfPermission(myActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        else {
            openAlbum();
        }
    }


    /**
     * 这里反馈是否得到权限，如果得到权限就继续openAlbum，得不到就会断掉
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 100:
                //如果得到权限
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                    openAlbum();
                else
                    Toast.makeText(myActivity, "你拒绝了权限", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 拍照 启动函数
     */
    private void goTakingPhoto(){
        String name="xxx"+".jpg";


        //创建  图片将要存放的位置
        file=new File("/sdcard/Pictures/",name);
        try{
            file.createNewFile();
            filePath = file.getPath();
            Log.e("fuck", "goTakingPhoto: "+ file.getPath());
            Log.e("fuck", "goTakingPhoto: "+ file.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }


        if(Build.VERSION.SDK_INT>=24)
            imageUri= FileProvider.getUriForFile(this.getContext(),"my.fileprovider",file);
        else
            imageUri=Uri.fromFile(file);

        //启动相机
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);

        isAlbum = false;
        return;
    }

    /**
     * 打开相册
     */
    private void openAlbum(){
        Intent AlbumIntent=new Intent("android.intent.action.GET_CONTENT");
        AlbumIntent.setType("image/*");
        startActivityForResult(AlbumIntent,CHOOSE_FROM_ALBUM);
    }

    /**
     * 接收 照完相 或者选择完照片 的 回归信号
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    //将拍摄的照片显示出来
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(myActivity.getContentResolver()
                                .openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                    }
                    catch (Exception e){e.printStackTrace();}
//                    this.getContext().getContentResolver().delete(imageUri, null, null);
                }
                break;

            case CHOOSE_FROM_ALBUM :
                if(resultCode==RESULT_OK)
                    //判断手机的android版本，高低版本用不同的函数方法去解决
                    if(Build.VERSION.SDK_INT>=19)
                        handleImageOnKitKat(data);
                    else
                        handleImageBeforeKitKat(data);
                break;


            default:
                break;
        }



    }

    @TargetApi(19)
    public void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        imageUri = uri;
        if(DocumentsContract.isDocumentUri(myActivity,uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];   //解析出数字格式的ID
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath
                        (MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

//                Toast.makeText(myActivity, "第一个", Toast.LENGTH_SHORT).show();
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri =  ContentUris.withAppendedId(Uri.parse("" +
                        "content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
//                Toast.makeText(myActivity, "第二个", Toast.LENGTH_SHORT).show();
            }
        }
        else if("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
//            Toast.makeText(myActivity, "第三个", Toast.LENGTH_SHORT).show();
        }
        else if("file".equalsIgnoreCase(uri.getScheme())) {
            Toast.makeText(myActivity, "第四个", Toast.LENGTH_SHORT).show();
            imagePath = uri.getPath();
        }


        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);

        Toast.makeText(myActivity, "第0个", Toast.LENGTH_SHORT).show();
    }

    private String getImagePath(Uri uri, String selection){
        String path=null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor=myActivity.getContentResolver().query(uri,null,selection,null,null);

        if(cursor!=null){
            if(cursor.moveToFirst())
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();

        }
        return path;
    }

    /**
     * 通过 图片的位置，可以通过  BitmapFactory 解析得到 bitmap，然后imageView就可以直接setImageBitmap
     * @param imagePath
     */
    private void displayImage(String imagePath){
        if(imagePath!=null) {
            Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        }
        else
            Toast.makeText(myActivity, "failed to get image", Toast.LENGTH_SHORT).show();

        isAlbum = true;
    }

}
