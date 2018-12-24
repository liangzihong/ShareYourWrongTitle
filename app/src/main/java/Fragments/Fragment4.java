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
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.liangzihong.viewpager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Activitys.MainActivity;
import Activitys.StartActivity;
import Application.MyApplication;
import BmobModels.BComment;
import BmobModels.BProfilePhoto;
import BmobModels.BWrongTitle;
import MyUtils.util1;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static android.app.Activity.RESULT_OK;
import MyUtils.util1;

/**
 * Created by Liang Zihong on 2018/2/8.
 */

public class Fragment4 extends Fragment {

    public static final int CHOOSE_FROM_ALBUM=1;
    public static final int TAKE_PHOTO=0;
    private Uri imageUri=null;
    private File file;
    private String filePath;
    private Activity myActivity;

    private CircleImageView imageView;
    private TextView NameTextView;
    private Button NameButton;
    private Button ProfileButton;
    private Button LogoutButton;
    private CardView bg;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        myActivity = this.getActivity();

        View view = inflater.inflate(R.layout.view4,container,false);
        imageView = (CircleImageView)view.findViewById(R.id.view4_profile);
        NameButton = (Button)view.findViewById(R.id.view4_name_button);
        ProfileButton = (Button) view.findViewById(R.id.view4_profile_button);
        NameTextView = (TextView)view.findViewById(R.id.view4_name);
        LogoutButton = (Button)view.findViewById(R.id.view4_logout_button);
        bg = (CardView) view.findViewById(R.id.view4_profile_bg);
        init();

        return view;
    }

    // 初始化
    private void init() {

//        MyApplication app = (MyApplication) getActivity().getApplication();

        String currentUserName = MyApplication.CurrentUserName;

        NameTextView.setText(currentUserName);

        // 换头像
        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });

        // 换名字
        NameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_edit();
            }
        });

        // 注销
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivity.startPictureActivity(myActivity);
            }
        });

        loadUserProfilePhoto();

    }

// -------------------------------------------------------------------------------------------------

    public void alert_edit(){
        final EditText et = new EditText(myActivity);
        new AlertDialog.Builder(myActivity).setTitle("请输入新名字")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (et.getText().equals("") == false) {
//
//                            final MyApplication app = (MyApplication) getActivity().getApplication();
                            // user表的update
                            // 如果改名成功，需要在 MyApplication改，在 bmob中改，在textName中改
                            final String newName = et.getText()+"";
                            BmobUser newBu = new BmobUser();
                            newBu.setUsername(et.getText()+"");
                            BmobUser bu = BmobUser.getCurrentUser(BmobUser.class);
                            newBu.update(bu.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {   // 更新成功
                                        MyApplication.CurrentUserName =newName;
                                        NameTextView.setText(newName);
                                        new AlertDialog.Builder(myActivity).setTitle("更名成功")
                                                .setMessage("你的用户名已修改，请记住哦~~").show();

                                        util1.modifyName();


                                    }
                                    else{
                                        // 不和旧名一样，但是失败，说明有名字重合
                                        if (newName.equals(MyApplication.CurrentUserName) == false){
                                            new AlertDialog.Builder(myActivity).setTitle("更名失败")
                                                    .setMessage("你想更改的名字已经被注册了哦~~\n请换另一个名字吧~~").show();
                                        }
                                    }
                                }
                            });

                        }


                    }
                }).setNegativeButton("取消",null).show();
    }


// -----------------------------------------------------------------------------------------------------------------------------




    // 选择头像的一堆函数

    // 打开那个选择框
    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this.getContext());
        normalDialog.setTitle("选择头像照片的途径");
        normalDialog.setMessage("打开");
        normalDialog.setPositiveButton("相册",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        applyPermission();
                    }
                });
        normalDialog.setNegativeButton("拍照",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goTakingPhoto();
                    }
                });
        // 显示
        normalDialog.show();
    }

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
        }catch (Exception e){
            e.printStackTrace();
        }


        if(Build.VERSION.SDK_INT>=24)
            imageUri= FileProvider.getUriForFile(this.getContext(),"wrongtitle2.fileprovider",file);
        else
            imageUri=Uri.fromFile(file);

        //启动相机
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
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

                    // 将照片传上服务器
                    uploadProfileToServer(new File(filePath));


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
        GaussianBlurFromUri(imageUri);


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
        uploadProfileToServer(new File(imagePath));
    }



    // 更新头像
    private void uploadProfileToServer(final File file) {
//        final MyApplication app = (MyApplication) myActivity.getApplication();
        final String CurrentUserId = MyApplication.CurrentUserId;

        // 根据id 获取到数据表的项， 然后获取头像的objectid，然后上传头像图片，再根据objectId进行数据库头像的修改
        BmobQuery<BProfilePhoto> query = new BmobQuery<BProfilePhoto>();
        query.addWhereEqualTo("userId", CurrentUserId);
        query.findObjects(new FindListener<BProfilePhoto>() {
            @Override
            public void done(List<BProfilePhoto> list, BmobException e) {
                if (e==null){
                    // 再更新即可
                    final String objectId = list.get(0).getObjectId();
                    final BmobFile bFile = new BmobFile(file);
                    Log.e("fuck", "fragment4 上传头像的文件:文件名字为"+ file.getAbsolutePath() );
                    Log.e("fuck", "fragment4 上传头像的文件:文件是否存在"+ file.exists() );
                    Log.e("fuck", "fragment4 :头像表ID为"+ objectId );

                    bFile.uploadblock(new UploadFileListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {

                                BProfilePhoto newPro = new BProfilePhoto();
                                newPro.setValue("profilePhotoFile", bFile);
                                newPro.update(objectId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(myActivity, "更新头像完成", Toast.LENGTH_SHORT).show();
                                            String profileUrl = bFile.getUrl();
                                            MyApplication.CurrentProfileUrl = profileUrl;
                                            util1.modifyProfileUrl();


//                                            BmobQuery<BWrongTitle> query3 = new BmobQuery<BWrongTitle>();
//                                            query3.addWhereEqualTo("userId", MyApplication.CurrentUserId);
//                                            query3.findObjects(new FindListener<BWrongTitle>() {
//                                                @Override
//                                                public void done(List<BWrongTitle> list, BmobException e) {
//                                                    if(e==null)
//                                                    {
//
//                                                        Log.e("lost", "读取数据成功");
//                                                        List<BmobObject> update_list = new ArrayList<>();
//                                                        Log.e("lost", "读取数据成功"+"没经过了 list.size "+ (list == null));
//                                                        Log.e("lost", "读取数据成功"+"没经过了 list.size "+ list.size());
//                                                        for (int i = 0; i < list.size(); i++) {
//                                                            BWrongTitle bWrongTitle = list.get(i);
//                                                            bWrongTitle.setProfileUrl(MyApplication.CurrentProfileUrl);
//                                                            update_list.add(bWrongTitle);
//                                                        }
//                                                        Log.e("lost", "读取数据成功"+"经过了 list.size");
//
//
//                                                        new BmobBatch().updateBatch(update_list).doBatch(new QueryListListener<BatchResult>() {
//
//                                                            @Override
//                                                            public void done(List<BatchResult> results, BmobException e) {
//                                                                Log.e("lost", "读取数据成功"+"经过了updateBatch");
//                                                                if (e == null) {
//                                                                    for (int i = 0; i < results.size(); i++) {
//                                                                        BatchResult result = results.get(i);
//                                                                        BmobException ex = result.getError();
//                                                                        if (ex == null) {
//                                                                            Log.e("lost", "done: 错题表更改头像url成功");
//                                                                        } else {
//                                                                            Log.e("lost", "done: 错题表更改头像url失败");
//                                                                        }
//                                                                    }
//                                                                } else {
//                                                                    Log.e("lost", "done: 错题表更改头像url失败");
//                                                                }
//                                                            }
//                                                        });
//                                                    }
//                                                    else{
//                                                        Log.e("lost", "失败"+e.toString());
//                                                    }
//                                                }
//                                            });


                                        } else {
                                            Toast.makeText(myActivity, "更新头像失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(MyApplication.getContext(), "更改头像时上传图片失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(myActivity, "找不到objectID", Toast.LENGTH_SHORT).show();
                    return ;
                }
            }
        });


    }

    // 通过 uri来进行高斯模糊
    private void GaussianBlurFromUri( Uri uri) {
        if (uri.equals("") == false) {
            Glide.with(myActivity)
                    .load(uri)
                    .dontAnimate()
                    //第二个参数是圆角半径，第三个是模糊程度，2-5之间个人感觉比较好。
                    .bitmapTransform(new BlurTransformation(myActivity, 20, 2))
                    .into(new ViewTarget<View, GlideDrawable>(bg) {
                        //括号里为需要加载的控件
                        @Override
                        public void onResourceReady(GlideDrawable resource,
                                                    GlideAnimation<? super GlideDrawable> glideAnimation) {
                            this.view.setBackground(resource.getCurrent());
                        }
                    });
            Glide.with(myActivity).load(uri).into(imageView);
        }
    }

    // 通过 网址 url 来进行高斯模糊
    private void GaussianBlurFromUrl( String url) {
        Glide.with(myActivity)
                .load(url)
                .dontAnimate()
                //第二个参数是圆角半径，第三个是模糊程度，2-5之间个人感觉比较好。
                .bitmapTransform(new BlurTransformation(myActivity, 20, 2))
                .into(new ViewTarget<View, GlideDrawable>(bg) {
                    //括号里为需要加载的控件
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });
        Glide.with(myActivity).load(url).into(imageView);
    }


    // 进入加载原始头像
    private void loadUserProfilePhoto(){
//        MyApplication app = (MyApplication) myActivity.getApplication();
        String CurrentUserId = MyApplication.CurrentUserId;

        BmobQuery<BProfilePhoto> query = new BmobQuery<BProfilePhoto>();
        query.addWhereEqualTo("userId", CurrentUserId);
        query.findObjects(new FindListener<BProfilePhoto>() {
            @Override
            public void done(List<BProfilePhoto> list, BmobException e) {
                if (e==null){
                    BmobFile bFile = list.get(0).getProfilePhotoFile();
                    String url = bFile.getUrl();
                    MyApplication.CurrentProfileUrl = url;
                    GaussianBlurFromUrl(url);

                }
            }
        });
    }





}


